package gov.iti.jets.server.controller;

import gov.iti.jets.common.network.server.IConnection;
import gov.iti.jets.common.network.server.IServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class IServerController extends UnicastRemoteObject implements IServer {

    public IServerController() throws RemoteException {
        super();
    }

    @Override
    public void sendMessage(long chatId, String senderId, String message)
    {

    }
}
