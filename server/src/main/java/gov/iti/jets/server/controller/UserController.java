package gov.iti.jets.server.controller;


import gov.iti.jets.common.dto.UserDto;
import gov.iti.jets.common.dto.UserSessionDto;
import gov.iti.jets.common.dto.registration.UserRegistrationDto;
import gov.iti.jets.common.network.server.UserRemote;
import gov.iti.jets.server.service.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserController extends UnicastRemoteObject implements UserRemote {

    private UserService userService;
    public UserController() throws RemoteException {
        super();
        userService = new UserService();
    }
//
    public void setOnlineStatus(String phone,String status) throws RemoteException {
        userService.setOnlineStatus(phone,status);
    }

    public UserSessionDto register(UserRegistrationDto userRegistrationDto) throws RemoteException {
            return userService.register( userRegistrationDto);
    }

    public UserSessionDto login(String phone, String password) throws RemoteException {
        return userService.login( phone,  password);
    }
    public void logout(String phone) throws RemoteException
    {
        userService.logout(phone);
    }

//    @Override
//    public void updateProfile(UserDto userDto) throws RemoteException {
//        userService.editUser(userDto);
//    }
}
