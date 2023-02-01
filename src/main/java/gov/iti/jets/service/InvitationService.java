package gov.iti.jets.service;

import gov.iti.jets.entity.Invitation;
import gov.iti.jets.mapper.UserMapper;
import gov.iti.jets.persistence.dao.InvitationDao;
import gov.iti.jets.persistence.dao.UserDao;

public class InvitationService {



    private InvitationDao dao;
    public InvitationService ()
    {
        dao = new InvitationDao();
    }
}
