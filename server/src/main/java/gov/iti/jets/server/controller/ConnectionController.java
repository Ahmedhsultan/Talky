package gov.iti.jets.server.controller;


import gov.iti.jets.common.dto.ConnectionDto;
import gov.iti.jets.common.network.server.IConnection;
import gov.iti.jets.common.util.Constants;
import gov.iti.jets.server.Util.Queues.ConnectedClientsMap;
import gov.iti.jets.server.service.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ConnectionController extends UnicastRemoteObject implements IConnection {
    public UserService userService;
    public ConnectionController() throws RemoteException {
        super();
        userService = new UserService();
    }

    @Override
    public void connect(ConnectionDto connectionDto) throws RemoteException {
        ConnectedClientsMap.getList().put(connectionDto.getUserDto().getId(), connectionDto);
        userService.setOnlineStatus(connectionDto.getUserDto().getId(), Constants.ONLINE_STATUS_AVAILABLE);
    }

    @Override
    public void disConnect(String userId) throws RemoteException {
         userService.setOnlineStatus(userId, Constants.ONLINE_STATUS_OFFLINE);
         if (ConnectedClientsMap.getList().containsKey(userId))
            ConnectedClientsMap.getList().remove(userId);
    }
}
