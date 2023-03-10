package gov.iti.jets.server.service;


import gov.iti.jets.common.dto.InvitationDto;
import gov.iti.jets.server.controller.IServerController;
import gov.iti.jets.server.entity.Invitation;
import gov.iti.jets.server.entity.User;
import gov.iti.jets.server.mapper.InvitationMapper;
import gov.iti.jets.server.persistence.dao.InvitationDao;
import gov.iti.jets.server.persistence.dao.UserDao;

import java.rmi.RemoteException;
import java.sql.Date;

public class InvitationService {

    private InvitationDao invitationDao;
    private UserDao userDao;
    private Invitation invitation;

    public InvitationService ()
    {
        invitationDao = new InvitationDao();
        userDao = new UserDao();
        invitation = new Invitation();
    }


    public InvitationDto sendInvitation(String senderID, String receiverID) throws RemoteException {

        if(userDao.findById(receiverID) == null) {
            throw new RemoteException("Phone Number Not Found!!");
        }
        createInvitationEntity(senderID, receiverID);

        //inserting invitation into invitations table
        invitationDao.insert(invitation);

        InvitationDto invitationDto = new InvitationMapper().toDTO(invitation);
        User u=userDao.findById(receiverID);

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
        invitationDao.deleteById(id);
        //Notify other user
        IServerController iServerController = new IServerController();
        iServerController.addFriend(invitation.getSenderId(),invitation.getReceiverId());
    }

    public void refuseInvitation(long id) throws RemoteException {
        //Get invitation from db then delete it
        invitationDao.deleteById(id);
    }

//    public void acceptInvitation(String invitationID) {
//
//        new FriendsDao().insert();
//
//    }
}
