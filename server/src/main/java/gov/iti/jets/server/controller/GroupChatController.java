package gov.iti.jets.server.controller;

import gov.iti.jets.common.network.server.IGroupChat;
import gov.iti.jets.common.network.server.ServerInvitation;
import gov.iti.jets.server.service.GroupService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class GroupChatController extends UnicastRemoteObject implements IGroupChat {
    GroupService groupService;
    protected GroupChatController() throws RemoteException  {
        groupService = new GroupService();
    }

    @Override
    public void createGroup(List<String> members) throws RemoteException {
        groupService.createGroup(members);
    }

    @Override
    public void addMember() throws RemoteException {

    }

    @Override
    public void deleteMember() throws RemoteException {

    }
}
