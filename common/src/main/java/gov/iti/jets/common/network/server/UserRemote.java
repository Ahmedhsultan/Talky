package gov.iti.jets.common.network.server;


import gov.iti.jets.common.dto.UserDto;
import gov.iti.jets.common.dto.UserSessionDto;
import gov.iti.jets.common.dto.registration.UserRegistrationDto;
import gov.iti.jets.common.network.client.IClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserRemote extends Remote {
    public void setOnlineStatus(String phone,String status) throws RemoteException;
    public UserSessionDto register(UserRegistrationDto userRegistrationDto, IClient iClient) throws RemoteException;
    public UserSessionDto login(String phone, String password, IClient iClient) throws RemoteException;
    public void logout(String phone) throws RemoteException;
}
