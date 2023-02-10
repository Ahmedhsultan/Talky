package gov.iti.jets.server.persistence.dao;


import gov.iti.jets.server.entity.Chat;
import gov.iti.jets.server.persistence.DBManagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatDao extends BaseDaoImpl<Chat, Long>{

    public ChatDao() {
        super(Chat.class);
    }

    public Chat insertChat(Chat entity) throws SQLException {
        String query = "insert into chat values(?,?,?,?,?);";

        try (Connection connection = DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getImgPath());
            statement.setDate(4, entity.getCreated_on());
            statement.setDate(5, entity.getModified_on());
            statement.executeUpdate();
            ResultSet resultSet =  statement.getGeneratedKeys();
            if(resultSet.next())
            {
                entity.setId(resultSet.getLong(1));
            }else{
                throw new SQLException();
            }
            return entity;

        }
    }

    @Override
    public void insert(Chat entity) throws SQLException {
        String query = "insert into chat values(?,?,?,?,?);";

        try (Connection connection = DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getImgPath());
            statement.setDate(4, entity.getCreated_on());
            statement.setDate(5, entity.getModified_on());
            statement.executeUpdate();

        }
    }

    @Override
    public void update(Chat entity) {

    }

    @Override
    public List<Chat> resultSetToList(ResultSet resultSet) throws SQLException {
        List<Chat> chats = new ArrayList<>();
        try{
            while (resultSet.next()) {
                Chat chat = Chat.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .imgPath(resultSet.getString("picture_icon"))
                        .modified_on(resultSet.getDate("modified_on"))
                        .created_on(resultSet.getDate("created_on"))
                        .build();
                chats.add(chat);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return chats;
    }
}
