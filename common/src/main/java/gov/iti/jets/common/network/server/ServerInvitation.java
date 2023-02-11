package gov.iti.jets.common.network.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInvitation extends Remote {

    void sendInvitation(String senderID, String receiverID) throws RemoteException;
    void acceptInvitation(long id) throws RemoteException;
    void rejectInvitation(long id) throws RemoteException;
}