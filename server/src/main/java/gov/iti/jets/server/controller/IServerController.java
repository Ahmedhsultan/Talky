package gov.iti.jets.server.controller;

import gov.iti.jets.common.network.server.IServer;
import gov.iti.jets.server.service.ChatUserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class IServerController extends UnicastRemoteObject implements IServer {

    private ChatUserService chatUserService;
    public IServerController() throws RemoteException {
        super();
        chatUserService = new ChatUserService();
    }

    @Override
    public void sendMessage(long chatId, String senderId, String message) throws RemoteException
    {
        chatUserService.sendMessage( chatId,  senderId, message);
    }
}
