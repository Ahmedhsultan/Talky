package gov.iti.jets;


import gov.iti.jets.entity.User;
import gov.iti.jets.persistence.DBManagement;
import gov.iti.jets.persistence.dao.BaseDao;
import gov.iti.jets.persistence.dao.BaseDaoImpl;
import gov.iti.jets.service.UserService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main
{
    public static void main( String[] args ) throws SQLException {
        List<User> user = new LinkedList<>();
        BaseDaoImpl<User,String> bd = new BaseDaoImpl<User, String>(new User()) {
            @Override
            public List<User> resultSetToList(ResultSet result) throws SQLException {
                List<User> user = new LinkedList<>();
                while(result.next()) {
                    user.add(User.builder().phoneNumber(result.getString("id"))
                            .name(result.getString("name"))
                            .email(result.getString("email"))
//                            .picture(result.getString("picture"))
                            .password(result.getString("password_hash"))
                            .sultPassword(result.getString("password_sult"))
                            .gender(result.getString("gender"))
                            .country(result.getString("country"))
                            .dateOfBirth(result.getDate("date_of_birth"))
                            .createdOn(result.getDate("created_on"))
                            .build());
                }
                return user;
            }
        };
        User u = bd.findById("01111315033");
        System.out.println(u.getPhoneNumber());

//        User user = new User(){};
//        UserService service = new UserService();
//        service.deleteById("s");
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
