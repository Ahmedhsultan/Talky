package gov.iti.jets.server.controller;

import gov.iti.jets.common.dto.ContactDto;
import gov.iti.jets.common.dto.UserDto;
import gov.iti.jets.common.network.client.IClient;
import gov.iti.jets.common.network.server.IServer;
import gov.iti.jets.server.Util.Queues.ConnectedClientsMap;
import gov.iti.jets.server.entity.Friends;
import gov.iti.jets.server.service.ChatUserService;
import gov.iti.jets.server.service.FriendsService;
import gov.iti.jets.server.service.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class IServerController extends UnicastRemoteObject implements IServer {

    private ChatUserService chatUserService;
    private UserService userService;
    private FriendsService friendsService;
    public IServerController() throws RemoteException {
        super();
        chatUserService = new ChatUserService();
        userService = new UserService();
        friendsService = new FriendsService();
    }

    @Override
    public void sendMessage(long chatId, String senderId, String message) throws RemoteException
    {
        chatUserService.sendMessage( chatId,  senderId, message);
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
        //Get users dto for both users
        UserDto user1 = userService.getUser(id1);
        UserDto user2 = userService.getUser(id2);

        //Notify client to add this user by callBack
        IClient iClient1 = ConnectedClientsMap.getList().get(user1).getIClient();
        iClient1.addFriend(user2);
        IClient iClient2 = ConnectedClientsMap.getList().get(user2).getIClient();
        iClient2.addFriend(user1);
    }

    @Override
    public void removeFriend(String id1, String id2) throws RemoteException {
        //Remove friendship from db
        friendsService.removeFriend(id1,id2);
        //Get users dto for both users
        UserDto user1 = userService.getUser(id1);
        UserDto user2 = userService.getUser(id2);

        //Notify client to remove this user by callBack
        IClient iClient1 = ConnectedClientsMap.getList().get(user1).getIClient();
        iClient1.removeFriend(user2);
        IClient iClient2 = ConnectedClientsMap.getList().get(user2).getIClient();
        iClient2.removeFriend(user1);
    }

    @Override
    public void editUser(UserDto userDto) throws RemoteException {
        //Edit user date in db
        UserDto newUserDto = userService.editUser(userDto);
        //Get user friends
        ArrayList<ContactDto> friendsList = friendsService.getListOfFriends(userDto.getId());

        //Notify all user friends with the new user data
        IClient iClient =  null;
        for (ContactDto element : friendsList){
            iClient = ConnectedClientsMap.getList().get(element.getPhoneNumber()).getIClient();
            iClient.editUser(newUserDto);
        }
    }
}
