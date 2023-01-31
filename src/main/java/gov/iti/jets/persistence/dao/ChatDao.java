package gov.iti.jets.persistence.dao;

import gov.iti.jets.entity.Chat;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatDao extends BaseDaoImpl<Chat, Integer>{

    public ChatDao() {
        super(Chat.class);
    }

    @Override
    public List<Chat> resultSetToList(ResultSet resultSet) throws SQLException {
        List<Chat> chats = new ArrayList<>();
        try{
            while (resultSet.next()) {
                Chat chat = Chat.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .picture_icon(resultSet.getString("picture_icon"))
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

    @Override
    public void insert(Chat entity) {

    }

    @Override
    public void update(Chat entity) {

    }
}
