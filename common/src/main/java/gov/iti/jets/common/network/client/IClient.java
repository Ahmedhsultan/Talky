package gov.iti.jets.common.network.client;

import gov.iti.jets.common.dto.ContactDto;
import gov.iti.jets.common.dto.InvitationDto;
import gov.iti.jets.common.dto.MessageDto;
import gov.iti.jets.common.dto.UserDto;
import gov.iti.jets.common.dto.UserSessionDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public interface IClient extends Remote {
    public void receive() throws RemoteException;
    public void receiveMessage(long chatId, MessageDto messageDto) throws RemoteException;
    public void addToGroup(String userId, long chatId) throws RemoteException;
    public void removefromGroup(String userId, long chatId) throws RemoteException;
    public void addFriend(List<ContactDto> contactDtoList) throws RemoteException;
    public void removeFriend(ContactDto contactDto) throws RemoteException;
    public void editUser(ContactDto contactDto) throws RemoteException;
    public void readFile(long chatId, String senderId, byte[] bytes ,String fileName ) throws RemoteException;
    public void receiveInvitation(InvitationDto invitationDto) throws RemoteException;
<<<<<<< HEAD
    public void receiveMessageBot(long chatId, String senderId, String message, String messageFromBot) throws RemoteException ;

    }
=======
    public void addNewSessetion(UserSessionDto userSessionDto) throws RemoteException;
}
>>>>>>> origin/develop
