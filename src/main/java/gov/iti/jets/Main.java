package gov.iti.jets;


import gov.iti.jets.entity.User;
import gov.iti.jets.persistence.DBManagement;
import gov.iti.jets.service.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main
{
    public static void main( String[] args )
    {
//        User user = new User(){};
        UserService service = new UserService();
        service.deleteById("s");
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
