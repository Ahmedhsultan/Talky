package gov.iti.jets.network;

import gov.iti.jets.dto.registration.UserRegistrationDto;
import gov.iti.jets.dto.UserSessionDto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserRemote extends Remote {

    public void setOnlineStatus(String phone,String status) throws RemoteException;
    public UserSessionDto register(UserRegistrationDto userRegistrationDto) throws RemoteException;
    public UserSessionDto login(String phone, String password) throws RemoteException;
    public void logout(String phone) throws RemoteException;


}
