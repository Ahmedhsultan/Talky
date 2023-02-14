package gov.iti.jets.common.network.client;

import gov.iti.jets.common.dto.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface IClient extends Remote {
//    static Map<Long, MessageDto>change = new HashMap<>();
    public void receive() throws RemoteException;
    public void receiveMessage(long chatId, MessageDto messageDto) throws RemoteException;
    public void addToGroup(String userId, long chatId) throws RemoteException;
    public void removefromGroup(String userId, long chatId) throws RemoteException;
    public void addFriend(List<ContactDto> contactDtoList) throws RemoteException;
    public void removeFriend(ContactDto contactDto) throws RemoteException;
    public void editUser(ContactDto contactDto) throws RemoteException;
    public void readFile(long chatId, String senderId, byte[] bytes ,String fileName ) throws RemoteException;
    public void receiveInvitation(InvitationDto invitationDto) throws RemoteException;
    public void addNewSessetion(UserSessionDto userSessionDto) throws RemoteException;
}
