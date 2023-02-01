package gov.iti.jets.network;

import gov.iti.jets.dto.registration.UserRegistrationDto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserRemote extends Remote {

    public void setOnlineStatus(String phone,String status);
    public void register(UserRegistrationDto userRegistrationDto) throws RemoteException;
    public void login(String phone, String password) throws RemoteException;
    public void logout(String phone) throws RemoteException;


}
