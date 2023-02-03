package gov.iti.jets.service;

import gov.iti.jets.dto.ChatDto;
import gov.iti.jets.dto.ContactDto;
import gov.iti.jets.dto.UserSessionDto;
import gov.iti.jets.entity.*;
import gov.iti.jets.mapper.ChatMapper;
import gov.iti.jets.mapper.UserMapper;
import gov.iti.jets.persistence.dao.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserSessionService {
    private User user;
    private UserDao userDao;
    private ChatDao chatDao;
    private FriendsDao friendsDao;
    private ChatUserDao chatUserDao;
    private NotificationDao notificationDao;
    private UserMapper userMapper;
    private ChatMapper chatMapper;
    private UserSessionDto userSessionDto;

    public UserSessionService(User user) {
        this.user = user;
        userDao = new UserDao();
        chatDao = new ChatDao();
        friendsDao = new FriendsDao();
        chatUserDao = new ChatUserDao();
        notificationDao = new NotificationDao();
        userMapper = new UserMapper();
        chatMapper = new ChatMapper();
    }

    public UserSessionDto getSessionDto() {
        //Get user chats from database and map it to dto
        List<ChatUser> chatUserList = chatUserDao.getChatsByUserId(user.getId());
        List<Chat> chatList = chatUserList.stream().map(x -> chatDao.findById(x.getChat_id())).toList();
        List<ChatDto> chatDtoList = chatList.stream().map(x -> chatMapper.toDTO(x))
                .sorted((x,y)-> x.getModified_on().compareTo(y.getModified_on()))
                .toList();

        //Get user contacts
        List<Friends> contactList = friendsDao.findAllById(user.getId());
        List<String> idsList = contactList.stream().map(x -> new ArrayList<String>(){{
                    add(x.getId1());
                    add(x.getId2());
                }})
                .flatMap(x -> x.stream()).toList();
        //Convert all contacts to set to remove duplicated user
        Set<User> userSet = idsList.stream().map(x -> userDao.findById(x)).collect(Collectors.toSet());
        //Map all contacts to dto
        List<ContactDto> userDtoList = userSet.stream().map(x -> userMapper.toContactDTO(x)).toList();

        //Get all user notification
        List<Notification> notificationList = notificationDao.getNotificationsByUserId(user.getId())
                .stream()
                .sorted((x,y)-> x.getCreated_on().compareTo(y.getCreated_on()))
                .toList();

        //Create session and return to user
        userSessionDto = UserSessionDto.builder()
                .user(userMapper.toDTO(user))
                .chatListDto(chatDtoList)
                .contactListDto(userDtoList)
                .notificationListDto(notificationList)
                .build();

        return userSessionDto;
    }
}
