package gov.iti.jets.common.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
    public void receive() throws RemoteException;
}
