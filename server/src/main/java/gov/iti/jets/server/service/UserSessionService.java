package gov.iti.jets.server.service;


import gov.iti.jets.common.dto.*;
import gov.iti.jets.server.entity.Chat;
import gov.iti.jets.server.entity.ChatUser;
import gov.iti.jets.server.entity.Friends;
import gov.iti.jets.server.entity.User;
import gov.iti.jets.server.mapper.ChatMapper;
import gov.iti.jets.server.mapper.InvitationMapper;
import gov.iti.jets.server.mapper.NotificationMapper;
import gov.iti.jets.server.mapper.UserMapper;
import gov.iti.jets.server.persistence.dao.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This is service which responsible for sending session to user once he loged in
 * this session contain all user contacts data, chats data, notifications and user data
 * All these lists are sorted according to creation and modification date
 */
public class UserSessionService {
    private User user;
    private UserDao userDao;
    private ChatDao chatDao;
    private FriendsDao friendsDao;
    private ChatUserDao chatUserDao;
    private NotificationDao notificationDao;
    private InvitationDao invitationDao;
    private UserMapper userMapper;
    private ChatMapper chatMapper;
    private NotificationMapper notificationMapper;
    private InvitationMapper invitationMapper;
    private UserSessionDto userSessionDto;
    private FriendsService friendsService;

    public UserSessionService(User user) {
        this.user = user;
        userDao = new UserDao();
        chatDao = new ChatDao();
        friendsDao = new FriendsDao();
        chatUserDao = new ChatUserDao();
        notificationDao = new NotificationDao();
        invitationDao = new InvitationDao();
        userMapper = new UserMapper();
        chatMapper = new ChatMapper();
        notificationMapper = new NotificationMapper();
        invitationMapper = new InvitationMapper();
        friendsService = new FriendsService();
    }

    public UserSessionDto getSessionDto() {
        //Get user chats from database and map it to dto then order by modified date
        List<ChatUser> chatUserList = chatUserDao.getChatsByUserId(user.getId());
        List<Chat> chatList = chatUserList.stream().map(x -> chatDao.findById(x.getId())).toList();
        ArrayList<ChatDto> chatDtoList = chatList.stream().map(x -> chatMapper.toDTO(x))
                .sorted((x,y)-> x.getModified_on().compareTo(y.getModified_on()))
                .collect(Collectors.toCollection(ArrayList::new));
        //Add chat members to chatdto
        chatDtoList.stream().forEach(x ->{
                List<ChatUser> chatMembersList = chatUserDao.getMembersByChatId(x.getId());
                ArrayList<String> chatMembersIdsList = chatMembersList.stream().map(y->y.getUser_id()).collect(Collectors.toCollection(ArrayList::new));
                x.setMembersIds(chatMembersIdsList);
        });

        //Get list of user friends
        ArrayList<ContactDto> userDtoList = friendsService.getListOfFriends(user.getId());

        //Get all user notification then order by created date
        ArrayList<NotificationDto> notificationList = notificationDao.getNotificationsByUserId(user.getId())
                .stream()
                .sorted((x,y)-> x.getCreated_on().compareTo(y.getCreated_on()))
                .map( x -> notificationMapper.toDTO(x))
                .collect(Collectors.toCollection(ArrayList::new));

        //Get all user invitation then order by created date
        ArrayList<InvitationDto> invitationList = invitationDao.getInvitationByReceiverId(user.getId())
                .stream()
                .sorted((x,y)-> x.getCreatedOn().compareTo(y.getCreatedOn()))
                .map( x -> invitationMapper.toDTO(x))
                .collect(Collectors.toCollection(ArrayList::new));

        //Create session and return to user
        userSessionDto = UserSessionDto.builder()
                .user(userMapper.toDTO(user))
                .chatListDto(chatDtoList)
                .contactListDto(userDtoList)
                .notificationListDto(notificationList)
                .invitationListDto(invitationList)
                .build();

        return userSessionDto;
    }
}
