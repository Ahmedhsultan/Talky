package gov.iti.jets.common.network.server;


import gov.iti.jets.common.dto.ConnectionDto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IConnection extends Remote {
    public void connect(ConnectionDto connectionDto) throws RemoteException;
    public void disConnect(ConnectionDto connectionDto) throws RemoteException;
}
