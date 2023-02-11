package gov.iti.jets.server.service;

import gov.iti.jets.common.dto.ChatDto;
import gov.iti.jets.common.dto.UserDto;
import gov.iti.jets.common.util.Constants;
import gov.iti.jets.server.entity.Chat;
import gov.iti.jets.server.mapper.ChatMapper;
import gov.iti.jets.server.persistence.dao.ChatDao;

import java.io.IOException;
import java.net.URLDecoder;
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
            saveUChatImage(dto);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to Add Chat!!!");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to Save Chat's Image!!!");

        }
        return chat;
    }

    public void saveUChatImage(ChatDto dto) throws IOException {
        String path = Constants.chatImagesDir+dto.getImgPath();
        Constants.byteArrayToImage(dto.getImage(), URLDecoder.decode(path, "UTF-8"));
    }
    public byte[] getChatImage(ChatDto dto) throws IOException {
        String path = Constants.chatImagesDir+dto.getImgPath();
        return Constants.imageToByteArray(path);
    }

}
