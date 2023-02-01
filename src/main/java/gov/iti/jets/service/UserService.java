package gov.iti.jets.service;

import gov.iti.jets.dto.UserDto;
import gov.iti.jets.dto.registration.UserRegistrationDto;
import gov.iti.jets.entity.User;
import gov.iti.jets.mapper.UserMapper;
import gov.iti.jets.persistence.DBManagement;
import gov.iti.jets.persistence.dao.UserDao;
import gov.iti.jets.util.Constants;
import gov.iti.jets.util.Validation;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserService {

    private UserDao dao;
    private UserMapper userMapper;

    public UserService ()
   {
       dao = new UserDao();
       userMapper = new UserMapper();
   }


    public UserDto register(UserRegistrationDto userRegistrationDto) throws RemoteException {

        try {

            if(!Validation.validatePhoneNumber(userRegistrationDto.getUserDto().getPhoneNumber()))
            {
                throw new RemoteException("Invalid Phone Number!!");
            }
            if(!Validation.validatePassword(userRegistrationDto.getPassword()))
            {
                throw new RemoteException("Invalid Password!!");
            }
            User user = dao.findById(userRegistrationDto.getUserDto().getPhoneNumber());
            if(user!=null)
            {
                throw new RemoteException("Phone Number Already Found !!");
            }
//            String hashedPass = Constants.hashPassword(userRegistrationDto.getPassword());
            dao.insert(userMapper.regDtoToEntity(userRegistrationDto));
//            setOnlineStatus(userRegistrationDto.getUserDto().getPhoneNumber(), Constants.ONLINE_STATUS_AVAILABLE);
        }catch (SQLException ex){
            ex.printStackTrace();
            throw new RemoteException("Failed to register, please try again !!");
        }
        return userRegistrationDto.getUserDto();

    }

    public void login(String phone, String password) throws RemoteException {
        if(!Validation.validatePhoneNumber(phone))
        {
            throw new RemoteException("Invalid Phone Number!!");
        }
        if(!Validation.validatePassword(password))
        {
            throw new RemoteException("Invalid Password!!");
        }
        User user = dao.findById(phone);
        if(user==null)
        {
            throw new RemoteException("Phone Number Not Found !!");
        }
        String hashedPass = Constants.hashPassword(password);
        if(!hashedPass.equals(user.getPassword()))
        {
            throw new RemoteException("Phone Number Not Found !!");
        }
        if( hashedPass ==null)
        {
            throw new RemoteException("Phone Number Already Found !!");
        }
        else{
//            userRegistrationDto.setPassword(hashedPass);
        }
//        dao.insert(userMapper.regDtoToEntity(userRegistrationDto));
//        return userRegistrationDto.getUs;
    }

    public void setOnlineStatus(String phone, String status){
        try {
            dao.setOnlineStatus(phone, status);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
