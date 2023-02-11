package gov.iti.jets.common.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInvitation extends Remote {

    void sendInvitation(String senderID, ClientInvitation clientInvitation, String receiverID) throws RemoteException;
    void acceptInvitation(String id) throws RemoteException;
    void rejectInvitation(String id) throws RemoteException;
}
