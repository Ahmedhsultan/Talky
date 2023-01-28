package gov.iti.jets.persistence.dao;

import gov.iti.jets.entity.User;
import gov.iti.jets.persistence.DBManagement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements BaseDao<User, String> {


    @Override
    public List<User> findAll() {
        List<User> users=null;
        String query = "select * from user;";
        try (PreparedStatement st = DBManagement.getInstance().getConnection().prepareStatement(query)) {
            ResultSet result = st.executeQuery();

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
        try (PreparedStatement state = DBManagement.getInstance().getConnection().prepareStatement("select * from user where phone_number = '"+phoneNumber+"';")) {
            ResultSet result = state.executeQuery();

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
        String query = "insert into user values(?,?,?,?,?,?,?,?,?,?,?,?);";

        try (PreparedStatement statement = DBManagement.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, entity.getPhoneNumber());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getPicture());
            statement.setString(5, entity.getPassword());
            statement.setString(6, entity.getSultPassword());
            statement.setString(7, entity.getGender());
            statement.setString(8, entity.getCountry());
            statement.setDate(9, entity.getDateOfBirth());
            statement.setString(10, entity.getBotMode());
            statement.setString(11, entity.getIsOnlineStatus());
            statement.setString(12, entity.getBotMode());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(User entity) {
        String query = "update  user set " +
                "phone_number =?" +
                ",name=?" +
                " ,email=?" +
                " ,picture=?" +
                " ,password_hash=?" +
                " ,password_sult=?" +
                " ,gender=?" +
                " ,country=?" +
                " ,date_of_birth=?" +
                " ,created_on=?" +
                " ,is_online_status=?" +
                " ,bot_mode=?" +
                " where phone_number = ?";

        try (PreparedStatement statement = DBManagement.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, entity.getPhoneNumber());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getPicture());
            statement.setString(5, entity.getPassword());
            statement.setString(6, entity.getSultPassword());
            statement.setString(7, entity.getGender());
            statement.setString(8, entity.getCountry());
            statement.setDate(9, entity.getDateOfBirth());
            statement.setString(10, entity.getBotMode());
            statement.setString(11, entity.getIsOnlineStatus());
            statement.setString(12, entity.getBotMode());
            statement.setString(13, entity.getPhoneNumber());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void deleteById(String phoneNumber) {
        String query = "delete from user where phone_number='"+phoneNumber+"';";
        try (PreparedStatement statement = DBManagement.getInstance().getConnection().prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
