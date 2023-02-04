package gov.iti.jets.controller;

import gov.iti.jets.dto.ConnectionDto;
import gov.iti.jets.dto.UserDto;
import gov.iti.jets.network.IClient;
import gov.iti.jets.network.IConnection;
import gov.iti.jets.util.ConnectedClientsList;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ConnectionController extends UnicastRemoteObject implements IConnection {
    public ConnectionController() throws RemoteException {
        super();
    }

    @Override
    public void connect(ConnectionDto connectionDto) throws RemoteException{
        ConnectedClientsList.getList().add(connectionDto);
    }

    @Override
    public void disConnect(ConnectionDto connectionDto) throws RemoteException{
        if(ConnectedClientsList.getList().contains(connectionDto))
            ConnectedClientsList.getList().remove(connectionDto);
    }
}
