package gov.iti.jets.common.network;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;

public interface IClient extends Remote {
    public void receive() throws RemoteException;
    public void receiveMessage(long chatId, String senderId, String message) throws RemoteException;
}
