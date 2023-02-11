package gov.iti.jets.server.persistence.dao;


import gov.iti.jets.common.util.Constants;
import gov.iti.jets.server.entity.ChatUser;
import gov.iti.jets.server.persistence.DBManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatUserDao extends BaseDaoImpl<ChatUser, Integer> {

    public ChatUserDao() {
        super(ChatUser.class);
    }

    public List<ChatUser> getChatsByUserId(String userId) {
        //Write select query by ID
        String query = "SELECT * FROM ChatUser WHERE user_id = " + userId + " ;";

        try (Connection connection = DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            //Execute the query
            ResultSet resultSet = statement.executeQuery();

            //Convert resultset to List
            List<ChatUser> list = resultSetToList(resultSet);

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insert(List<ChatUser> entities) throws SQLException {
        String query = "insert into chatuser values(?,?);";

        try (Connection connection = DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            for (ChatUser entity : entities) {
                statement.setLong(1, entity.getId());
                statement.setString(2, entity.getUser_id());
                statement.executeUpdate();
            }
        }
    }


    @Override
    public void insert(ChatUser entity) throws SQLException {

    }

    @Override
    public void update(ChatUser entity) {

    }

    public void deleteIdFromChat(List<ChatUser> entities) throws SQLException{
        String query = "DELETE FROM chatuser WHERE id = ? AND user_id = ?;";

        try (Connection connection = DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            for (ChatUser entity : entities) {
                statement.setLong(1, entity.getId());
                statement.setString(2, entity.getUser_id());
                statement.executeUpdate();
            }
        }
    }

    @Override
    public List<ChatUser> resultSetToList(ResultSet resultSet) throws SQLException {
        List<ChatUser> chatUserList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                ChatUser chatUser = ChatUser.builder()
                        .id(resultSet.getLong("chat_id"))
                        .user_id(resultSet.getString("user_id"))
                        .build();
                chatUserList.add(chatUser);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return chatUserList;
    }

    public List<String> getOnlineUsersByChat(long chatId) throws SQLException {
        //Write select query by ID
        String query = "SELECT user_id FROM ChatUser WHERE id = " + chatId + " and user_id in (select id from user where is_online_status !=" + Constants.ONLINE_STATUS_OFFLINE + ");";
        List<String> list = new ArrayList<>();

        try (Connection connection = DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            //Execute the query
            ResultSet resultSet = statement.executeQuery();
            //Convert resultset to List
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }

        }
        return list;

    }
}
