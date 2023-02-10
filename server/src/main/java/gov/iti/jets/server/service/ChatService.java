package gov.iti.jets.server.service;

import gov.iti.jets.common.dto.ChatDto;
import gov.iti.jets.server.entity.Chat;
import gov.iti.jets.server.mapper.ChatMapper;
import gov.iti.jets.server.persistence.dao.ChatDao;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class ChatService {

    private ChatDao dao;
    private ChatMapper chatMapper;

    public ChatService ()
    {
        dao = new ChatDao();
        chatMapper = new ChatMapper();
    }

    public Chat addChat(ChatDto dto) throws RemoteException
    {
        Chat chat = null;
        try {
            chat = dao.insertChat(chatMapper.toEntity(dto));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to Add Chat!!!");
        }
        return chat;
    }
}