package gov.iti.jets.server.controller;

import gov.iti.jets.common.dto.*;
import gov.iti.jets.common.network.client.IClient;
import gov.iti.jets.common.network.server.IServer;
import gov.iti.jets.common.util.Constants;
import gov.iti.jets.server.Util.Queues.ConnectedClientsMap;
import gov.iti.jets.server.entity.Chat;
import gov.iti.jets.server.entity.Invitation;
import gov.iti.jets.server.entity.User;
import gov.iti.jets.server.mapper.UserMapper;
import gov.iti.jets.server.service.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class IServerController extends UnicastRemoteObject implements IServer {

    private ChatUserService chatUserService;
    private ChatService chatService;
    private UserService userService;
    private FriendsService friendsService;
    private UserMapper userMapper;
    private FileTransferService fileTransferService;

    public IServerController() throws RemoteException {
        super();
        chatUserService = new ChatUserService();
        userService = new UserService();
        friendsService = new FriendsService();
        userMapper = new UserMapper();
        fileTransferService = new FileTransferService();
        chatService = new ChatService();
    }

    @Override
    public void sendMessage(long chatId, MessageDto messageDto) throws RemoteException
    {
        System.out.println("sendmsg");
        chatUserService.sendMessage( chatId, messageDto);
    }

    @Override
    public void addToGroup(String userId, long chatId) throws RemoteException {
        chatUserService.addToGroup(userId, chatId);
    }

    @Override
    public void removefromGroup(String userId, long chatId) throws RemoteException {
        chatUserService.removeFromGroup(userId, chatId);
    }

    @Override
    public void addFriend(long id, String id1, String id2) throws RemoteException {
        //Add chat table
        ChatDto chatDto = new ChatDto();
        chatDto.setName(id1+","+id2);
        chatDto.setModified_on(new Date(System.currentTimeMillis()));
        chatDto.setType(Constants.CHAT_ONE_TO_ONE);
        Chat chat = chatService.addChat(chatDto);
        //Add chatUser
        ChatUserDto chatUserDto1 = new ChatUserDto();
        chatUserDto1.setUserId(id1);
        chatUserDto1.setId(chat.getId());
        ChatUserDto chatUserDto2 = new ChatUserDto();
        chatUserDto2.setUserId(id2);
        chatUserDto2.setId(chat.getId());
        List<ChatUserDto> chatUserDtoList = new ArrayList<>();
        chatUserDtoList.add(chatUserDto1);
        chatUserDtoList.add(chatUserDto2);
        chatUserService.addChatGroup(chatUserDtoList);
        //Add friendship from db
        friendsService.addFriend(id1,id2);

        //Mapping to ContactDto
        UserDto userDto1 = userService.getUser(id1);
        User user1 = userMapper.toEntity(userDto1);
        ContactDto contactDto1 = userMapper.toContactDTO(user1);

        UserDto userDto2 = userService.getUser(id2);
        User user2 = userMapper.toEntity(userDto2);
        ContactDto contactDto2 = userMapper.toContactDTO(user2);

        //Notify client to add this user by callBack
        if (ConnectedClientsMap.getList().containsKey(user1.getId())){
            IClient iClient1 = ConnectedClientsMap.getList().get(user1.getId()).getIClient();
            ArrayList<ContactDto> contactDtoList1 = new ArrayList<>();
            contactDtoList1.add(contactDto2);
            iClient1.addFriend(id, contactDtoList1);
//            iClient1.removeInvitation(id);
        }

        if (ConnectedClientsMap.getList().containsKey(user2.getId())) {
            IClient iClient2 = ConnectedClientsMap.getList().get(user2.getId()).getIClient();
            ArrayList<ContactDto> contactDtoList2 = new ArrayList<>();
            contactDtoList2.add(contactDto1);
            iClient2.addFriend(id, contactDtoList2);
//            iClient2.removeInvitation(id);
        }
    }

    @Override
    public void removeFriend(String id1, String id2) throws RemoteException {
        //Remove friendship from db
        friendsService.removeFriend(id1,id2);

        //Mapping to ContactDto
        UserDto userDto1 = userService.getUser(id1);
        User user1 = userMapper.toEntity(userDto1);
        ContactDto contactDto1 = userMapper.toContactDTO(user1);

        UserDto userDto2 = userService.getUser(id2);
        User user2 = userMapper.toEntity(userDto2);
        ContactDto contactDto2 = userMapper.toContactDTO(user2);

        //Notify client to remove this user by callBack
        IClient iClient1 = ConnectedClientsMap.getList().get(user1).getIClient();
        iClient1.removeFriend(contactDto1);
        IClient iClient2 = ConnectedClientsMap.getList().get(user2).getIClient();
        iClient2.removeFriend(contactDto2);
    }

    @Override
    public void editUser(UserDto userDto) throws RemoteException {
        //Edit user date in db
        UserDto newUserDto = userService.editUser(userDto);
        //Mapping to ContactDto
        User user = userMapper.toEntity(newUserDto);
        ContactDto contactDto = userMapper.toContactDTO(user);
        //Get user friends
        ArrayList<ContactDto> friendsList = friendsService.getListOfFriends(userDto.getId());

        //Notify all user friends with the new user data
        IClient iClient =  null;
        for (ContactDto element : friendsList){
            iClient = ConnectedClientsMap.getList().get(element.getPhoneNumber()).getIClient();
            iClient.editUser(contactDto);
        }
    }

    @Override
    public synchronized void sendFile(long chatId, String senderId, byte[] bytes, String fileName) throws RemoteException
    {
        fileTransferService.sendFile( chatId,  senderId,  bytes,  fileName);
    }

    @Override
    public void sendAnnouncement(String message) throws RemoteException {
            new ServerService().sendAnnouncement(message);
    }


}
