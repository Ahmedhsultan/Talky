package gov.iti.jets.controller;

import gov.iti.jets.dto.UserDto;
import gov.iti.jets.dto.registration.UserRegistrationDto;
import gov.iti.jets.entity.User;
import gov.iti.jets.network.UserRemote;
import gov.iti.jets.service.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserController extends UnicastRemoteObject implements UserRemote {

    private UserService userService = new UserService();
    public UserController() throws RemoteException {
    }
//
//    public void setOnlineStatus(String phone,String status)
//    {
//        userService.setOnlineStatus(phone,status);
//    }

    public void register(UserRegistrationDto userRegistrationDto) throws RemoteException {
        System.out.println("controller");
            userService.register( userRegistrationDto);
    }

    public void login(String phone, String password) throws RemoteException {
        userService.login( phone,  password);
    }
    public void logout(UserRegistrationDto phone) throws RemoteException
    {
        throw new RemoteException("hiii");
    }
}
