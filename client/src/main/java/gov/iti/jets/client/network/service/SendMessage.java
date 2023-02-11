package gov.iti.jets.client.network.service;

import gov.iti.jets.common.network.server.IServer;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class SendMessage {
    public synchronized static void send(long chatId, String senderId, String message) throws RemoteException, NotBoundException {
        IServer iServer = RMIManager.lookUpIServer();
        iServer.sendMessage(chatId, senderId, message);
    }
}
