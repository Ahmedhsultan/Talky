package gov.iti.jets.common.network.client;

import gov.iti.jets.common.dto.UserDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;

public interface IClient extends Remote {
    public void receive() throws RemoteException;
    public void receiveMessage(long chatId, String senderId, String message) throws RemoteException;
    public void addToGroup(String userId, long chatId) throws RemoteException;
    public void removefromGroup(String userId, long chatId) throws RemoteException;
    public void addFriend(UserDto userDto) throws RemoteException;
    public void removeFriend(UserDto userDto) throws RemoteException;
    public void editUser(UserDto userDto) throws RemoteException;
}
