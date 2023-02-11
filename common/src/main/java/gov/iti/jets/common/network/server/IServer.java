package gov.iti.jets.common.network.server;

import gov.iti.jets.common.dto.UserDto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {

    public void sendMessage(long chatId, String senderId, String message) throws RemoteException;
    public void addToGroup(String userId, long chatId) throws RemoteException;
    public void removefromGroup(String userId, long chatId) throws RemoteException;
    public void addFriend(String sender, String receiver) throws RemoteException;
    public void removeFriend(String sender, String receiver) throws RemoteException;
    public void editUser(UserDto userDto) throws RemoteException;
    public  void  sendFile(long chatId, String senderId, byte[] bytes ,String fileName ) throws RemoteException;
}
