package gov.iti.jets;


import gov.iti.jets.dto.InvitationDto;
import gov.iti.jets.entity.User;
import gov.iti.jets.mapper.InvitationMapper;
import gov.iti.jets.persistence.DBManagement;
import gov.iti.jets.service.InvitationService;
import gov.iti.jets.service.UserService;

import java.sql.Connection;


public class Main
{
    public static void main( String[] args )
    {
        UserService service = new UserService();
        User user = new User();
        user.setPhoneNumber("01111315066");
        user.setPassword("33333");
        user.setCountry("Egypt");
        user.setBotMode(false);
        InvitationDto invitationDto = new InvitationDto();
        invitationDto.setId(1);
        invitationDto.setSenderId("01111315066");
        invitationDto.setReceiverId("01111315033");
        invitationDto.setSeen(true);
        InvitationService invService = new InvitationService();
        InvitationMapper mapper = new InvitationMapper();
        invService.save(mapper.toEntity(invitationDto));
//        service.update(user);
//        System.out.println(service.findById("01111315033"));
        Connection con = DBManagement.getInstance().getConnection();
//
//        try {
//            Statement st = con.createStatement();
//
//            st.execute("insert into st values('abdo');");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        System.out.println( "Hello World!" );
    }
}
