package gov.iti.jets.service;

import gov.iti.jets.Main;
import gov.iti.jets.dto.UserDto;
import gov.iti.jets.dto.UserSessionDto;
import gov.iti.jets.dto.registration.UserRegistrationDto;
import gov.iti.jets.entity.User;
import gov.iti.jets.mapper.UserMapper;
import gov.iti.jets.persistence.DBManagement;
import gov.iti.jets.persistence.dao.UserDao;
import gov.iti.jets.util.Constants;
import gov.iti.jets.util.Validation;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
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
        try {
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
//            saveUserImage(userRegistrationDto.getUserDto());
            dao.insert(user);
            userSessionService=new UserSessionService(user);
            userSessionDto= userSessionService.getSessionDto();
        }catch (SQLException ex){
            ex.printStackTrace();
            throw new RemoteException("Failed to register, please try again !!");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to Save User's Image, please try again !!");
        }
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
        String path = getClass().getClassLoader().getResource("images/users").getPath()+"/"+dto.getImgPath();
        Constants.byteArrayToImage(dto.getImage(), path);
    }
    public byte[] getUserImage(UserDto dto) throws IOException {
        String path = getClass().getClassLoader().getResource("images/users").getPath()+"/"+dto.getImgPath();
        return Constants.imageToByteArray(path);
    }

//


}
