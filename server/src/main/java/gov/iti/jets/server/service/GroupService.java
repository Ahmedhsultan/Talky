package gov.iti.jets.server.service;

import gov.iti.jets.server.entity.Chat;
import gov.iti.jets.server.entity.ChatUser;
import gov.iti.jets.server.mapper.ChatMapper;
import gov.iti.jets.server.persistence.dao.ChatDao;
import gov.iti.jets.server.persistence.dao.ChatUserDao;
import gov.iti.jets.server.persistence.dao.UserDao;

import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class GroupService {

    ChatDao chatDao ;
    ChatMapper chatMapper;
    Chat chat;
    UserDao userDao;
    ChatUserDao chatUserDao;

    public GroupService(){
        chatDao = new ChatDao();
        chatMapper = new ChatMapper();
        userDao = new UserDao();
        chatUserDao= new ChatUserDao();
    }

    public void createGroup(List<String> members){
        try {
            createChatEntity(chat);
            chat =  chatDao.insertChat(chat);
            List<ChatUser>  entities = addMembers(chat.getId(), members);
            chatUserDao.insert(entities);  //insert all members
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createChatEntity(Chat ch) {
        chat.setName("");
        chat.setType("Group");
        chat.setCreated_on(new Date(System.currentTimeMillis()));
        chat.setModified_on(new Date(System.currentTimeMillis()));
    }

    private List<ChatUser> addMembers(long chatId, List<String> ids){
        List<ChatUser> members  = null;
        for(String id:ids){
            members.add(new ChatUser(chatId,id));
        }
        return  members;
    }
}
