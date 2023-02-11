package gov.iti.jets.server.service;


import gov.iti.jets.common.dto.InvitationDto;
import gov.iti.jets.common.network.client.ClientInvitation;
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
    private InvitationDto invitationDto;

    public InvitationService ()
    {
        invitationDao = new InvitationDao();
        userDao = new UserDao();
        invitation = new Invitation();
    }


    public void sendInvitation(String senderID, ClientInvitation clientInvitation, String receiverID) throws RemoteException {

        if(userDao.findById(receiverID) == null) {
            throw new RemoteException("Phone Number Not Found!!");
        }
        createInvitationEntity(senderID, receiverID);

        //inserting invitation into invitations table
        invitationDao.insert(invitation);

        invitationDto = new InvitationMapper().toDTO(invitation);
        User u=userDao.findById(receiverID);
        if(!u.getIsOnlineStatus().equals("Offline")) {
                clientInvitation.receiveInvitation(invitationDto);
        }

    }

    public void createInvitationEntity(String senderID, String receiverID) {
        invitation.setSeen(false);
        invitation.setStatus("add");
        invitation.setSenderId(senderID);
        invitation.setReceiverId(receiverID);
        long millis = System.currentTimeMillis();
        invitation.setCreatedOn(new Date(millis));
    }


//    public void acceptInvitation(String invitationID) {
//
//        new FriendsDao().insert();
//
//    }
}
