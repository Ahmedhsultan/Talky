package gov.iti.jets.persistence.dao;
import gov.iti.jets.entity.User;
import gov.iti.jets.persistence.DBManagement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends BaseDaoImpl<User, String> {

    public UserDao() {
        super(User.class);
    }
    @Override
    public List<User> resultSetToList(ResultSet result) {
        List<User> users = new ArrayList<>();
        try{
        while (result.next()) {
            User user = User.builder().id(result.getString("id"))
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
                    .botMode(result.getBoolean("bot_mode"))
                    .bio(result.getString("bio"))
                    .build();
            users.add(user);
        }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    @Override
    public void insert(User entity) {
        String query = "insert into user values(?,?,?,?,?,?,?,?,?,?,?,?,?);";

        try (Connection connection =DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            setUserStatement(statement, entity);
            System.out.println(statement.executeUpdate());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(User entity) {
        String query = "update  user set " +
                "id =?" +
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
                " ,bio=?" +
                " where id = ?";

        try (Connection connection =DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            setUserStatement(statement,entity);
            statement.setString(13, entity.getId());
            System.out.println(statement.executeUpdate());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setUserStatement(PreparedStatement statement, User entity)
    {
        try {
            statement.setString(1, entity.getId());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getPicture());
            statement.setString(5, entity.getPassword());
            statement.setString(6, entity.getSultPassword());
            statement.setString(7, entity.getGender());
            statement.setString(8, entity.getCountry());
            statement.setDate(9, entity.getDateOfBirth());
            statement.setBoolean(10, entity.isBotMode());
            statement.setString(11, entity.getIsOnlineStatus());
            statement.setBoolean(12, entity.isBotMode());
            statement.setString(13, entity.getBio());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
