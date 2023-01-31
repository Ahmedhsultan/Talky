package gov.iti.jets;


import gov.iti.jets.entity.Invitation;
import gov.iti.jets.entity.User;
import gov.iti.jets.persistence.DBManagement;
import gov.iti.jets.persistence.dao.BaseDao;
import gov.iti.jets.persistence.dao.BaseDaoImpl;
import gov.iti.jets.persistence.dao.UserDao;
import gov.iti.jets.service.InvitationService;
import gov.iti.jets.service.UserService;
import gov.iti.jets.util.Constants;
import gov.iti.jets.util.Validation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main
{
    public static void main( String[] args ) {

        Invitation inv = new Invitation();
     inv.setId(2);
     inv.setReceiverId("01111315099");
        inv.setSenderId("01111315033");
        InvitationService service = new InvitationService();
        System.out.println(Validation.validatePassword("01gfgg998kkk"));

//        System.out.println(service.deleteById(2l));

        System.out.println( "Hello World!" );
    }
}
