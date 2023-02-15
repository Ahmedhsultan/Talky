package gov.iti.jets.server.controller;


import gov.iti.jets.common.dto.ConnectionDto;
import gov.iti.jets.common.dto.UserDto;
import gov.iti.jets.common.dto.UserSessionDto;
import gov.iti.jets.common.dto.registration.UserRegistrationDto;
import gov.iti.jets.common.network.client.IClient;
import gov.iti.jets.common.network.server.UserRemote;
import gov.iti.jets.server.Util.Queues.ConnectedClientsMap;
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

    public UserSessionDto register(UserRegistrationDto userRegistrationDto, IClient iClient) throws RemoteException {
        ConnectionDto connectionDto =new ConnectionDto();
        connectionDto.setUserDto(userRegistrationDto.getUserDto());
        connectionDto.setIClient(iClient);

        ConnectionController connectionController = new ConnectionController();
        connectionController.connect(connectionDto);

        ConnectedClientsMap.getList().put(userRegistrationDto.getUserDto().getId(),connectionDto);
        return userService.register(userRegistrationDto);
    }

    public UserSessionDto login(String phone, String password, IClient iClient) throws RemoteException {
        if (ConnectedClientsMap.getList().containsKey(phone))
            throw new RemoteException("Other user User this Acount");
        //Get user session data from db
        UserSessionDto userSessionDto = userService.login(phone,  password);
        //Establish connection between user and server
        ConnectionDto connectionDto = new ConnectionDto();
        connectionDto.setUserDto(userSessionDto.getUser());
        connectionDto.setIClient(iClient);

        if (ConnectedClientsMap.getList().containsKey(phone))
            throw new RemoteException("Other user User this Acount");
        ConnectionController connectionController = new ConnectionController();
        connectionController.connect(connectionDto);
        //Notify user with his session data
        iClient.addNewSessetion(userSessionDto);

        return userSessionDto;
    }
    public void logout(String phone) throws RemoteException
    {
        //Remove from ConnectedClients List
        ConnectionController connectionController = new ConnectionController();
        connectionController.disConnect(phone);
    }
}
