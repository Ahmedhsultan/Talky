package gov.iti.jets.persistence.dao;

import gov.iti.jets.dto.BaseDto;
import gov.iti.jets.entity.User;
import gov.iti.jets.persistence.DBManagement;
import gov.iti.jets.service.BaseServiceImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements BaseDao<User, String> {


    @Override
    public List<User> findAll() {
        List<User> users=null;
        try (Statement st = DBManagement.getInstance().getConnection().createStatement()) {
            ResultSet result = st.executeQuery("select * from user;");

            if(result.next()) {
                users = new ArrayList<>();
                User user = User.builder().phoneNumber(result.getString("phone_number"))
                        .name(result.getString("name"))
                        .email(result.getString("email"))
                        .picture(result.getString("picture"))
                        .password(result.getString("password_hash"))
                        .sultPassword(result.getString("password_sult"))
                        .gender(result.getString("gender"))
                        .country(result.getString("country"))
                        .dateOfBirth(result.getDate("date_of_birth"))
                        .createdOn(result.getDate("created_on"))
                        .isOnlineStatus(result.getString("is_online_status"))
                        .botMode(result.getString("bot_mode"))
                        .build();
                users.add(user);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    @Override
    public User findById(String phoneNumber) {
        User user = null;
        try (Statement st = DBManagement.getInstance().getConnection().createStatement()) {
            ResultSet result = st.executeQuery("select * from user where phone_number = '"+phoneNumber+"';");

            if(result.next()) {
                 user = User.builder().phoneNumber(result.getString("phone_number"))
                        .name(result.getString("name"))
                        .email(result.getString("email"))
                        .picture(result.getString("picture"))
                        .password(result.getString("password_hash"))
                        .sultPassword(result.getString("password_sult"))
                        .gender(result.getString("gender"))
                        .country(result.getString("country"))
                        .dateOfBirth(result.getDate("date_of_birth"))
                        .createdOn(result.getDate("created_on"))
                        .isOnlineStatus(result.getString("is_online_status"))
                        .botMode(result.getString("bot_mode"))
                        .build();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public void save(User entity) {

    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void deleteById(String phoneNumber) {
        try (Statement st = DBManagement.getInstance().getConnection().createStatement()) {
            st.executeUpdate("delete from user where phone_number='"+phoneNumber+"';");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
