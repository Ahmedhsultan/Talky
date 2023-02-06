package gov.iti.jets.controller;

import gov.iti.jets.dto.UserDto;
import gov.iti.jets.dto.UserSessionDto;
import gov.iti.jets.dto.registration.UserRegistrationDto;
import gov.iti.jets.entity.User;
import gov.iti.jets.network.UserRemote;
import gov.iti.jets.service.UserService;

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
        System.out.println("controller");
            return userService.register( userRegistrationDto);
    }

    public UserSessionDto login(String phone, String password) throws RemoteException {
        return userService.login( phone,  password);
    }
    public void logout(String phone) throws RemoteException
    {
        userService.logout(phone);
    }
}
