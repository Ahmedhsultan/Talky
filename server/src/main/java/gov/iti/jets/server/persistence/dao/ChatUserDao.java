package gov.iti.jets.server.persistence.dao;


import gov.iti.jets.server.entity.ChatUser;
import gov.iti.jets.server.persistence.DBManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatUserDao extends BaseDaoImpl<ChatUser, Integer>{

    public ChatUserDao() {
        super(ChatUser.class);
    }

    public List<ChatUser> getChatsByUserId(String userId){
        //Write select query by ID
        String query = "SELECT * FROM ChatUser WHERE user_id = "+ userId +" ;";

        try(Connection connection = DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
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
            for(ChatUser entity: entities) {
                statement.setLong(1, entity.getId());
                statement.setString(2, entity.getUserId());
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

    @Override
    public List<ChatUser> resultSetToList(ResultSet resultSet) throws SQLException {
        List<ChatUser> chatUserList = new ArrayList<>();
        try{
            while (resultSet.next()) {
                ChatUser chatUser = ChatUser.builder()
                        .id(resultSet.getLong("chat_id"))
                        .userId(resultSet.getString("user_id"))
                        .build();
                chatUserList.add(chatUser);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return chatUserList;
    }
}
