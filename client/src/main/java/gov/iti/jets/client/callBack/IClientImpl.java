package gov.iti.jets.client.callBack;


import gov.iti.jets.client.Queues.*;
import gov.iti.jets.client.Util.AlertWindow;
import gov.iti.jets.client.Util.ClearQueues;
import gov.iti.jets.client.Util.ConnectionFlag;
import gov.iti.jets.client.network.service.PullOnlineUsersFromServer;
import gov.iti.jets.client.network.service.RMIManager;
import gov.iti.jets.common.dto.ContactDto;
import gov.iti.jets.common.dto.InvitationDto;
import gov.iti.jets.common.dto.MessageDto;
import gov.iti.jets.common.dto.UserSessionDto;
import gov.iti.jets.common.network.client.IClient;
import gov.iti.jets.common.network.server.IServer;
import gov.iti.jets.common.util.Constants;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class IClientImpl extends UnicastRemoteObject implements IClient {
    public IClientImpl() throws RemoteException {
    }

    @Override
    public void receive() throws RemoteException {
        System.out.println("recivemsg");
        ConnectionFlag.getInstance().connectedFlag = true;
    }

    @Override
    public void receiveMessage(long chatId, MessageDto messageDto) throws RemoteException {
//        System.out.println(chatId + messageDto.getMessage() + messageDto.getSenderId());

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(!MessagesQueue.getList().containsKey(chatId)){
                    ObservableList<MessageDto> messageDtoList = FXCollections.observableArrayList();
                    messageDtoList.add(messageDto);
                    MessagesQueue.getList().put(chatId,messageDtoList);
//            System.out.println(MessagesQueue.getList().get(chatId).get(MessagesQueue.getList().get(chatId).size()-1));

                }else{
                    MessagesQueue.getList().get(chatId).add(messageDto);

                   if(MessagesQueue.getList().containsKey(-1))
                       MessagesQueue.getList().remove(-1);
                   else{
                       ObservableList<MessageDto> l = FXCollections.observableArrayList();   //honors to Amr
                       l.add(new MessageDto());
                       MessagesQueue.getList().put(-1l, l);
                   }

                }

            }
        });
        MessagesQueue.change.clear();
        MessagesQueue.change.put(chatId, messageDto);

    }

    @Override
    public void addToGroup(String userId, long chatId) throws RemoteException {

    }

    @Override
    public void removefromGroup(String userId, long chatId) throws RemoteException {

    }

    @Override
    public void addFriend(long id, ArrayList<ContactDto> contactDtoList) throws RemoteException {
        //Add contact element to contact list
        for (ContactDto contactDto : contactDtoList)
            ContactList.getList().add(contactDto);
        removeInvitation(id);
    }

    @Override
    public void removeFriend(ContactDto contactDto) throws RemoteException {
        //Get contactDto element and remove it from the list
        ContactDto oldContactDto = ContactList.getList().stream().filter(x -> x.getPhoneNumber() == contactDto.getPhoneNumber()).toList().get(0);
        ContactList.getList().remove(oldContactDto);
    }

    @Override
    public void editUser(ContactDto contactDto) throws RemoteException {
        //Get old contactDto element and remove it from the list
        ContactDto oldContactDto = ContactList.getList().stream().filter(x -> x.getPhoneNumber() == contactDto.getPhoneNumber()).toList().get(0);
        ContactList.getList().remove(oldContactDto);
        //Add new contact element with new user data
        ContactList.getList().add(contactDto);
    }

    @Override
    public  synchronized  void readFile(long chatId, String senderId, byte[] bytes, String fileName) throws RemoteException
    {
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
            FileChannel channel = FileChannel.open(Paths.get(Constants.CHAT_FILES_DIR + fileName),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            channel.write(byteBuffer);
        }
         catch (IOException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to send File!!");
        }
    }

    @Override
    public void receiveInvitation(InvitationDto invitationDto) throws RemoteException {
        System.out.println("wasal client 2");
        //Add invitation to queue
        InvitationQueue.getList().add(invitationDto);
    }
    public void removeInvitation(long invitationId) throws RemoteException {
        //Add invitation to queue
        List<InvitationDto> invitationDto = InvitationQueue.getList().stream()
                .filter(x -> x.getId() == invitationId)
                .toList();
        if (invitationDto.stream().count() != 0)
            InvitationQueue.getList().removeAll(invitationDto);
    }

    @Override
    public void addNewSessetion(UserSessionDto userSessionDto) throws RemoteException {
        //Clear all queues
        ClearQueues.clearAllQueues();

        //Clear and add new session to contact list
        ContactList.getList().clear();
        ContactList.getList().addAll(userSessionDto.getContactListDto());
        //Clear and add new session to invitation queue
        InvitationQueue.getList().clear();
        InvitationQueue.getList().addAll(userSessionDto.getInvitationListDto());
        //Clear and add new session to chat queue
        ChatList.getList().clear();
        ChatList.getList().addAll(userSessionDto.getChatListDto());

        //Start Online pulling service
        PullOnlineUsersFromServer.getInstance();
    }
    @Override
    public void receiveAnnouncement(String message) throws RemoteException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                AlertWindow alertWindow = new AlertWindow(message);

            }
        });
    }

    @Override
    public void receiveMessageBot(long chatId, MessageDto messageDto, String messageFromBot) throws RemoteException {
        MessageDto responseMessage = new MessageDto();
        responseMessage.setMessage(messageFromBot);
        responseMessage.setSenderId(MyID.getInstance().getMyId());

        if(!MessagesQueue.getList().containsKey(chatId)){
            ObservableList<MessageDto> messageDtoList =  FXCollections.observableArrayList();
            messageDtoList.add(messageDto);
            MessagesQueue.getList().put(chatId,messageDtoList);
        }else{
            MessagesQueue.getList().get(chatId).add(messageDto);
        }
        IServer server = null;
        try {
            server = RMIManager.lookUpIServer();
        } catch (NotBoundException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to reply to Message");
        }
        server.sendMessage(chatId, responseMessage);

    }

}
