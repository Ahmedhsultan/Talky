package gov.iti.jets.server.controller;

import gov.iti.jets.common.dto.ContactDto;
import gov.iti.jets.common.dto.MessageDto;
import gov.iti.jets.common.dto.UserDto;
import gov.iti.jets.common.network.client.IClient;
import gov.iti.jets.common.network.server.IServer;
import gov.iti.jets.server.Util.Queues.ConnectedClientsMap;
import gov.iti.jets.server.entity.User;
import gov.iti.jets.server.mapper.UserMapper;
import gov.iti.jets.server.service.ChatUserService;
import gov.iti.jets.server.service.FileTransferService;
import gov.iti.jets.server.service.FriendsService;
import gov.iti.jets.server.service.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class IServerController extends UnicastRemoteObject implements IServer {

    private ChatUserService chatUserService;
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
    public void addFriend(String id1, String id2) throws RemoteException {
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
        IClient iClient1 = ConnectedClientsMap.getList().get(user1).getIClient();
        List<ContactDto> contactDtoList1 = new ArrayList<>();
        contactDtoList1.add(contactDto1);
        iClient1.addFriend(contactDtoList1);

        IClient iClient2 = ConnectedClientsMap.getList().get(user2).getIClient();
        List<ContactDto> contactDtoList2 = new ArrayList<>();
        contactDtoList2.add(contactDto2);
        iClient2.addFriend(contactDtoList2);
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
}
