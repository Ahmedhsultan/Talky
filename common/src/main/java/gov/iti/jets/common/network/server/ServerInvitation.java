package gov.iti.jets.common.network.server;

import gov.iti.jets.common.network.client.ClientInvitation;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInvitation extends Remote {

    void sendInvitation(String senderID, ClientInvitation clientInvitation, String receiverID) throws RemoteException;
    void acceptInvitation(String id) throws RemoteException;
    void rejectInvitation(String id) throws RemoteException;
}