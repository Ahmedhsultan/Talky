package gov.iti.jets.common.network.client;

import gov.iti.jets.common.dto.ContactDto;
import gov.iti.jets.common.dto.InvitationDto;
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
    public void addFriend(ContactDto contactDto) throws RemoteException;
    public void removeFriend(ContactDto contactDto) throws RemoteException;
    public void editUser(ContactDto contactDto) throws RemoteException;
    public void readFile(long chatId, String senderId, byte[] bytes ,String fileName ) throws RemoteException;
    public void receiveInvitation(InvitationDto invitationDto) throws RemoteException;
    public void receiveMessageBot(long chatId, String senderId, String message, String messageFromBot) throws RemoteException ;

    }
