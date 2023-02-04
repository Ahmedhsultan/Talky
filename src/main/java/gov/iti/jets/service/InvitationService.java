package gov.iti.jets.service;

import gov.iti.jets.dto.InvitationDto;
import gov.iti.jets.entity.Friends;
import gov.iti.jets.entity.Invitation;
import gov.iti.jets.mapper.InvitationMapper;
import gov.iti.jets.mapper.UserMapper;
import gov.iti.jets.network.ClientInvitation;
import gov.iti.jets.persistence.dao.FriendsDao;
import gov.iti.jets.persistence.dao.InvitationDao;
import gov.iti.jets.persistence.dao.UserDao;

import java.rmi.RemoteException;
import java.sql.Date;

public class InvitationService {

    private InvitationDao invitationDao;
    private UserDao userDao;
    private Invitation invitation;
    private InvitationDto invitationDto;
    private `int i =0;

    public InvitationService ()
    {
        invitationDao = new InvitationDao();
        userDao = new UserDao();
        invitation = new Invitation();
    }


    public void sendInvitation(String senderID, ClientInvitation clientInvitation, String receiverID) throws RemoteException {

        if(userDao.findById(receiverID) == null) {
            throw new RemoteException("Not Found!!");
        }
        createInvitationEntity(senderID, receiverID);

        //inserting invitation into invitations table
        invitationDao.insert(invitation);

        invitationDto = new InvitationMapper().toDTO(invitation);
        if(!userDao.findById(receiverID).getIsOnlineStatus().equals("offline")) {
                clientInvitation.receiveInvitation(invitationDto);
        }

    }

    public void createInvitationEntity(String senderID, String receiverID) {
        invitation.setId(i++);
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
