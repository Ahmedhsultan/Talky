package gov.iti.jets.server.service;

import gov.iti.jets.common.dto.ChatUserDto;
import gov.iti.jets.server.Util.Queues.ConnectedClientsMap;
import gov.iti.jets.server.entity.ChatUser;
import gov.iti.jets.server.mapper.ChatUserMapper;
import gov.iti.jets.server.persistence.dao.ChatUserDao;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public void sendMessage(long chatId, MessageDto messageDto) throws RemoteException {
        List<String> userIds = null;
        try {
            userIds = dao.getOnlineUsersByChat(chatId);
            if(userIds!=null) {
                for (String userId : userIds) {
                    try {
                        if(ConnectedClientsMap.getList().containsKey(userId))
                            ConnectedClientsMap.getList().get(userId).getIClient().receiveMessage(chatId, messageDto);
                    }catch (RemoteException re){
                        re.printStackTrace();
                    }
                    if(ConnectedClientsMap.getList().get(userId).getUserDto().isBotMode())
                    {
                        ConnectedClientsMap.getList().get(userId).getIClient().receiveMessageBot(chatId, senderId, message, talkToBot(message));

                    }else {
                        ConnectedClientsMap.getList().get(userId).getIClient().receiveMessage(chatId, senderId, message);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to Send Message!!");
        }
    }

    public void addToGroup(String userId, long chatId) throws RemoteException{
        ChatUser chatUser = new ChatUser();
        chatUser.setId(chatId);
        chatUser.setUser_id(userId);

        List<ChatUser> list = new ArrayList<ChatUser>();
        list.add(chatUser);

        try {
            dao.insert(list);
        } catch (SQLException e) {
            throw new RemoteException("Failed to add user to group!!");
        }
    }

    public void removeFromGroup(String userId, long chatId) throws RemoteException{
        ChatUser chatUser = new ChatUser();
        chatUser.setId(chatId);
        chatUser.setUser_id(userId);

        List<ChatUser> list = new ArrayList<ChatUser>();
        list.add(chatUser);

        try {
            dao.deleteIdFromChat(list);
        } catch (SQLException e) {
            throw new RemoteException("Failed to add user to group!!");
        }
    }

    private  String talkToBot(String message)
    {
        String result="";
        try {
            ChatterBotFactory factory = new ChatterBotFactory();
            ChatterBot bot2 = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
            ChatterBotSession bot2session = bot2.createSession();

            result = bot2session.think(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  result;
    }
}
