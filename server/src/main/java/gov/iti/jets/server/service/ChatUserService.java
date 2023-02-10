package gov.iti.jets.server.service;

import gov.iti.jets.common.dto.ChatUserDto;
import gov.iti.jets.server.entity.ChatUser;
import gov.iti.jets.server.mapper.ChatMapper;
import gov.iti.jets.server.mapper.ChatUserMapper;
import gov.iti.jets.server.persistence.dao.ChatDao;
import gov.iti.jets.server.persistence.dao.ChatUserDao;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public class ChatUserService {

    private ChatUserDao dao;
    private ChatUserMapper chatUserMapper;
    private  ChatService chatService;

    public ChatUserService ()
    {
        dao = new ChatUserDao();
        chatUserMapper = new ChatUserMapper();
        chatService  = new ChatService();
    }

    public void addChatGroup(List<ChatUserDto> chatUsers) throws RemoteException {
        try {
            dao.insert(chatUserMapper.toEntities(chatUsers));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to add Members!!!");
        }
    }
}
