package gov.iti.jets.common.network;


import gov.iti.jets.common.dto.UserDto;
import gov.iti.jets.common.dto.UserSessionDto;
import gov.iti.jets.common.dto.registration.UserRegistrationDto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserRemote extends Remote {
    public void setOnlineStatus(String phone,String status) throws RemoteException;
    public UserSessionDto register(UserRegistrationDto userRegistrationDto) throws RemoteException;
    public UserSessionDto login(String phone, String password) throws RemoteException;
    public void logout(String phone) throws RemoteException;
    public void updateProfile(UserDto userDto) throws RemoteException;
}
