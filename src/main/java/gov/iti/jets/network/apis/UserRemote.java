package gov.iti.jets.network.apis;

import gov.iti.jets.entity.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserRemote extends Remote {

    public void register(User user) throws RemoteException;

    public void login(String phone, String password) throws RemoteException;

}
