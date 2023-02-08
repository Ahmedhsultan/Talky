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

    @Override
    public void insert(ChatUser entity) {

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
                        .chat_id(resultSet.getInt("chat_id"))
                        .user_id(resultSet.getString("user_id"))
                        .build();
                chatUserList.add(chatUser);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return chatUserList;
    }
}
