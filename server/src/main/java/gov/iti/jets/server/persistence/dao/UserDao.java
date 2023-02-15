package gov.iti.jets.server.persistence.dao;


import gov.iti.jets.common.util.Constants;
import gov.iti.jets.server.entity.User;
import gov.iti.jets.server.persistence.DBManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends BaseDaoImpl<User, String> {

    public UserDao() {
        super(User.class);
    }
    @Override
    public List<User> resultSetToList(ResultSet result) throws SQLException {
        List<User> users = new ArrayList<>();
        while (result.next()) {
            User user = User.builder().id(result.getString("id"))
                    .name(result.getString("name"))
                    .email(result.getString("email"))
                    .imgPath(result.getString("img_path"))
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
        return users;
    }

    @Override
    public void insert(User entity) throws SQLException{
        String query = "insert into user values(?,?,?,?,?,?,?,?,?,?,?,?,?);";

        try (Connection connection = DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            setUserStatement(statement, entity);
            entity.setIsOnlineStatus(Constants.ONLINE_STATUS_OFFLINE);
            System.out.println(statement.executeUpdate());
        }
    }

    @Override
    public void update(User entity) throws SQLException{
        String query = "update  user set " +
                "id =?" +
                ",name=?" +
                " ,email=?" +
                " ,img_path=?" +
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
        }
    }



    private void setUserStatement(PreparedStatement statement, User entity)throws SQLException
    {
        statement.setString(1, entity.getId());
        statement.setString(2, entity.getName());
        statement.setString(3, entity.getEmail());
        statement.setString(4, entity.getImgPath());
        statement.setString(5, entity.getPassword());
        statement.setString(6, entity.getSultPassword());
        statement.setString(7, entity.getGender());
        statement.setString(8, entity.getCountry());
        statement.setDate(9, entity.getDateOfBirth());
        statement.setDate(10, entity.getCreatedOn());
        statement.setString(11, entity.getIsOnlineStatus());
        statement.setBoolean(12, entity.isBotMode());
        statement.setString(13, entity.getBio());
    }
    public void setOnlineStatus(String phone, String status)throws SQLException
    {
        String query = "update  user set " +
                " is_online_status=?" +
                " where id = ?";

        try (Connection connection =DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, status);
            statement.setString(2, phone);
            System.out.println(statement.executeUpdate());
        }
    }

    public void setAllOffline()throws SQLException
    {
        String query = "update  user set " +
                " is_online_status='offline';";

        try (Connection connection =DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            System.out.println(statement.executeUpdate());
        }
    }

//    public List<User> getOfflineUsers()throws SQLException
//    {
//        String query = "select * from  user where is_online_status='offline';";
//
//        try (Connection connection =DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
//            ResultSet resultSet = statement.executeQuery();
//
//            //Convert resultset to List
//            List<User> list = resultSetToList(resultSet);
//
//            return list;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

public void editProfile(User entity) throws SQLException{
    String query = "update  user set " +
            "name=?" +
            " ,img_path=?" +
            " ,is_online_status=?" +
            " ,bot_mode=?" +
            " ,bio=?" +
            " where id = ?";

    try (Connection connection =DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1,entity.getName());
        statement.setString(2,entity.getImgPath());
        statement.setString(3,entity.getIsOnlineStatus());
        statement.setBoolean(4,entity.isBotMode());
        statement.setString(5,entity.getBio());
        statement.setString(6, entity.getId());
        System.out.println(statement.executeUpdate());
    }
}




}
