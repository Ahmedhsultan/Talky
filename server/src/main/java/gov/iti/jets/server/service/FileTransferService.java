package gov.iti.jets.server.service;

import gov.iti.jets.common.util.Constants;
import gov.iti.jets.server.Util.Queues.ConnectedClientsMap;
import gov.iti.jets.server.mapper.ChatUserMapper;
import gov.iti.jets.server.mapper.UserMapper;
import gov.iti.jets.server.persistence.dao.ChatUserDao;
import gov.iti.jets.server.persistence.dao.UserDao;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public class FileTransferService {


    private ChatUserDao chatUserDao;

    public FileTransferService ()
    {
        chatUserDao = new ChatUserDao();

    }

    public void sendFile(long chatId, String senderId, byte[] bytes, String fileName) throws RemoteException {
        List<String> userIds = null;
        try {
            userIds = chatUserDao.getOnlineUsersByChat(chatId);
            if(userIds!=null) {
                for (String userId : userIds) {
                    ConnectedClientsMap.getList().get(userId).getIClient().readFile(chatId, senderId, bytes , fileName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to Send File!!");
        }

}

}
