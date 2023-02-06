package gov.iti.jets.network;

import gov.iti.jets.dto.ConnectionDto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IConnection extends Remote {
    public void connect(ConnectionDto connectionDto) throws RemoteException;
    public void disConnect(ConnectionDto connectionDto) throws RemoteException;
}
