package gov.iti.jets.client.callBack;


import gov.iti.jets.common.network.IClient;
import gov.iti.jets.common.util.ConnectionFlag;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CheckConnection extends UnicastRemoteObject implements IClient {
    public CheckConnection() throws RemoteException {
    }

    @Override
    public void receive() throws RemoteException {
        ConnectionFlag.getInstance().connectedFlag = true;
    }
}
