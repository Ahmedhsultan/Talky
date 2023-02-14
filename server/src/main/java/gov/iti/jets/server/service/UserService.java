package gov.iti.jets.server.service;


import gov.iti.jets.common.dto.UserCardDto;
import gov.iti.jets.common.dto.UserDto;
import gov.iti.jets.common.dto.UserSessionDto;
import gov.iti.jets.common.dto.registration.UserRegistrationDto;
import gov.iti.jets.common.util.Constants;
import gov.iti.jets.common.util.Validation;
import gov.iti.jets.server.Util.Queues.StatsLists;
import gov.iti.jets.server.Util.Queues.UsersList;
import gov.iti.jets.server.entity.User;
import gov.iti.jets.server.mapper.UserMapper;
import gov.iti.jets.server.persistence.dao.UserDao;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.chart.PieChart;

import java.io.IOException;
import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private UserDao dao;
    private UserMapper userMapper;
    private UserSessionService userSessionService;

    public UserService ()
   {
       dao = new UserDao();
       userMapper = new UserMapper();
   }


    public UserSessionDto register(UserRegistrationDto userRegistrationDto) throws RemoteException {

        UserSessionDto userSessionDto=null;
        if(!Validation.validatePhoneNumber(userRegistrationDto.getUserDto().getId()))
        {
            throw new RemoteException("Invalid Phone Number!!");
        }
        if(!Validation.validatePassword(userRegistrationDto.getPassword()))
        {
            throw new RemoteException("Invalid Password!!");
        }
        User user=null;
        User tempUser= dao.findById(userRegistrationDto.getUserDto().getId());
        if (tempUser!=null)
        {
            throw new RemoteException("Phone Number Already Found!!");
        }
        String hashedPass = Constants.hashPassword(userRegistrationDto.getPassword());
        userRegistrationDto.getUserDto().setIsOnlineStatus(Constants.ONLINE_STATUS_AVAILABLE);
        userRegistrationDto.setPassword(hashedPass);
        user = userMapper.regDtoToEntity(userRegistrationDto);
        try {
            saveUserImage(userRegistrationDto.getUserDto());
            dao.insert(user);
        }catch (SQLException ex){
            ex.printStackTrace();
            throw new RemoteException("Failed to register, please try again !!");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to Save User's Image, please try again !!");
        }
        userSessionService=new UserSessionService(user);
        userSessionDto= userSessionService.getSessionDto();
        StatsLists.getInstance().updateUserStats();
        return  userSessionDto;
    }

    public UserSessionDto login(String phone, String password) throws RemoteException {
            if(!Validation.validatePhoneNumber(phone))
            {
                throw new RemoteException("Invalid Phone Number!!");
            }
            User user = dao.findById(phone);
            if(user == null)
            {
                throw new RemoteException("Phone Number Not Found!!");
            }
            if(!Validation.validatePassword(password))
            {
                throw new RemoteException("Invalid Password!!");
            }

            String hashedPass = Constants.hashPassword(password);
            if(!hashedPass.equals(user.getPassword()))
            {
                throw new RemoteException("Invalid Password !!");
            }
            setOnlineStatus(phone, Constants.ONLINE_STATUS_AVAILABLE);
            userSessionService=new UserSessionService(user);
            UserSessionDto userSessionDto= userSessionService.getSessionDto();
            StatsLists.getInstance().updateUserStats();
            UsersList.getInstance().updateOnlineAndOfflineStats();
           return userSessionDto;
    }

    public void setOnlineStatus(String phone, String status) throws RemoteException {
        try {
            dao.setOnlineStatus(phone, status);
        } catch (SQLException e) {
            throw new RemoteException("Failed to Change status!!");
        }
    }
    public void logout(String phone) throws RemoteException {
        setOnlineStatus(phone,Constants.ONLINE_STATUS_OFFLINE);
    }

    public void saveUserImage(UserDto dto) throws IOException {
        System.out.println(Constants.MAIN_DIR);
        System.out.println(Constants.USER_IMAGES_DIR);
        String path = Constants.USER_IMAGES_DIR +dto.getImgPath();
        Constants.byteArrayToImage(dto.getImage(), URLDecoder.decode(path, "UTF-8"));
    }
    public byte[] getUserImage(UserDto dto) throws IOException {
        String path = Constants.USER_IMAGES_DIR +dto.getImgPath();
        return Constants.imageToByteArray(path);
    }
    public byte[] getUserImage(UserCardDto dto) throws IOException {
        String path = Constants.USER_IMAGES_DIR +dto.getImgPath();
        return Constants.imageToByteArray(path);
    }

    public UserDto editUser (UserDto userDto) throws RemoteException{
        User user = userMapper.toEntity(userDto);
        try {
            dao.editProfile(user);
            saveUserImage(userDto);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to edit user!!");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to edit user!!");

        }
        return getUser(userDto.getId());
    }
    public UserDto getUser(String id) throws RemoteException{
        User user = dao.findById(id);
        UserDto userDto = userMapper.toDTO(user);

        return  userDto;
    }
    public void setAllOffline(){
        try {
            dao.setAllOffline();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<UserDto> getAllUsers()
    {
        List<UserDto> dtos =null;
        List<User> users = dao.findAll();
        if(users!=null) {
            dtos = userMapper.toDTOs(users);
        }
        return  dtos;
    }
}
