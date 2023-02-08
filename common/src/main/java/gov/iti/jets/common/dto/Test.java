package gov.iti.jets.common.dto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Test extends Remote {
    public int run() throws RemoteException;
}
