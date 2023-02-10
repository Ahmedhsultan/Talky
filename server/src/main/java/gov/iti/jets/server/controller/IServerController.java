package gov.iti.jets.server.controller;

import gov.iti.jets.common.dto.UserDto;
import gov.iti.jets.common.network.server.IServer;
import gov.iti.jets.server.entity.Friends;
import gov.iti.jets.server.service.ChatUserService;
import gov.iti.jets.server.service.FriendsService;
import gov.iti.jets.server.service.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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
    public void addFriend(String sender, String receiver) throws RemoteException {
        friendsService.addFriend(sender,receiver);
    }

    @Override
    public void removeFriend(String sender, String receiver) throws RemoteException {
        friendsService.removeFriend(sender,receiver);
    }

    @Override
    public void editUser(UserDto userDto) throws RemoteException {
        userService.editUser(userDto);
    }
}
