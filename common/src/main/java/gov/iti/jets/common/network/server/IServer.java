package gov.iti.jets.common.network.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {

    public void sendMessage(long chatId, String senderId, String message) throws RemoteException;
}
