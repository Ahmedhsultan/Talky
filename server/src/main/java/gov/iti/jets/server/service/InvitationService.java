package gov.iti.jets.server.service;


import gov.iti.jets.common.dto.ContactDto;
import gov.iti.jets.common.dto.InvitationDto;
import gov.iti.jets.common.network.client.IClient;
import gov.iti.jets.server.Util.Queues.ConnectedClientsMap;
import gov.iti.jets.server.controller.IServerController;
import gov.iti.jets.server.entity.Invitation;
import gov.iti.jets.server.entity.User;
import gov.iti.jets.server.mapper.InvitationMapper;
import gov.iti.jets.server.persistence.dao.InvitationDao;
import gov.iti.jets.server.persistence.dao.UserDao;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class InvitationService {

    private InvitationDao invitationDao;
    private UserDao userDao;
    private Invitation invitation;
    private FriendsService friendsService;

    public InvitationService() {
        invitationDao = new InvitationDao();
        userDao = new UserDao();
        invitation = new Invitation();
        friendsService = new FriendsService();
    }


    public InvitationDto sendInvitation(String senderID, String receiverID) throws RemoteException {
        if (senderID.equals(receiverID)) {
            throw new RemoteException("Cant invite your self!!");
        }
        if (userDao.findById(receiverID) == null) {
            throw new RemoteException("Phone Number Not Found!!");
        }
        //Check if is there any invitation
        List<Invitation> invitations = invitationDao.getInvitationByReceiverId(receiverID);
        if (invitations != null){
            for (Invitation ele : invitations){
                if(ele.getSenderId().equals(senderID))
                    throw new RemoteException("This number has invited before!!");
            }
        }
        List<ContactDto> friendsList =  friendsService.getListOfFriends(senderID);
        if (friendsList != null){
            for (ContactDto ele : friendsList){
                if(ele.getId().equals(receiverID)){
                    throw new RemoteException("You are already friends!!");
                }
            }
        }
        invitations = invitationDao.getInvitationByReceiverId(senderID);
        if (invitations != null){
            for (Invitation ele : invitations){
                if(ele.getSenderId().equals(receiverID)){
                    acceptInvitation(ele.getId());
                    return null;
                }
            }
        }

        createInvitationEntity(senderID, receiverID);

        //inserting invitation into invitations table
        invitationDao.insert(invitation);

        InvitationDto invitationDto = new InvitationMapper().toDTO(invitation);
        User u = userDao.findById(receiverID);

        return invitationDto;
    }

    public void createInvitationEntity(String senderID, String receiverID) {
        invitation.setSeen(false);
        invitation.setStatus("add");
        invitation.setSenderId(senderID);
        invitation.setReceiverId(receiverID);
        long millis = System.currentTimeMillis();
        invitation.setCreatedOn(new Date(millis));
    }

    public void acceptInvitation(long id) throws RemoteException {
        //Get invitation from db then delete it
        Invitation invitation = invitationDao.findById(id);

        //Notify other user
        IServerController iServerController = new IServerController();
        iServerController.addFriend(id, invitation.getSenderId(), invitation.getReceiverId());
        invitationDao.deleteById(id);
    }

    public void refuseInvitation(long id) throws RemoteException {
        //Get invitation from db then delete it
        Invitation invitation = invitationDao.findById(id);
        //Get invitation from db then delete it
        invitationDao.deleteById(id);

        //Notify client to add this user by callBack
        if (ConnectedClientsMap.getList().containsKey(invitation.getReceiverId())){
            IClient iClient1 = ConnectedClientsMap.getList().get(invitation.getReceiverId()).getIClient();
            iClient1.removeInvitation(id);
        }

        if (ConnectedClientsMap.getList().containsKey(invitation.getSenderId())) {
            IClient iClient2 = ConnectedClientsMap.getList().get(invitation.getSenderId()).getIClient();
            iClient2.removeInvitation(id);
        }
    }
}
