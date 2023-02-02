package gov.iti.jets.service;

import gov.iti.jets.dto.ChatDto;
import gov.iti.jets.dto.ContactDto;
import gov.iti.jets.dto.SessionDto;
import gov.iti.jets.entity.Chat;
import gov.iti.jets.entity.ChatUser;
import gov.iti.jets.entity.Friends;
import gov.iti.jets.entity.User;
import gov.iti.jets.mapper.ChatMapper;
import gov.iti.jets.mapper.UserMapper;
import gov.iti.jets.persistence.dao.ChatDao;
import gov.iti.jets.persistence.dao.ChatUserDao;
import gov.iti.jets.persistence.dao.FriendsDao;
import gov.iti.jets.persistence.dao.UserDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GetUserSession {
    private SessionDto sessionDto;
    private ChatDao chatDao;
    private FriendsDao friendsDao;
    private ChatUserDao chatUserDao;
    private UserMapper userMapper;
    private UserDao userDao;
    private ChatMapper chatMapper;
    private User user;

    public GetUserSession(User user) {
        this.user = user;
        userDao = new UserDao();
        friendsDao = new FriendsDao();
        chatDao = new ChatDao();
        chatUserDao = new ChatUserDao();
        userMapper = new UserMapper();
        chatMapper = new ChatMapper();
    }

    public SessionDto getSessionDto() {
        List<ChatUser> chatUserList = chatUserDao.getChatsByUserId(user.getId());
        List<Chat> chatList = chatUserList.stream().map(x -> chatDao.findById(x.getChat_id())).toList();
        List<ChatDto> chatDtoList = chatList.stream().map(x -> chatMapper.toDTO(x)).toList();

        List<Friends> contactList = friendsDao.findAllById(user.getId());
        List<String> idsList = contactList.stream().map(x -> new ArrayList<String>(){{
                    add(x.getId1());
                    add(x.getId2());
                }})
                .flatMap(x -> x.stream()).toList();

        Set<User> userSet = idsList.stream().map(x -> userDao.findById(x)).collect(Collectors.toSet());

        List<ContactDto> userDtoList = userSet.stream().map(x -> userMapper.toContactDTO(x)).toList();

                sessionDto = SessionDto.builder()
                        .user(userMapper.toDTO(user))
                        .chatListDto(chatDtoList)
                        .contactListDto(userDtoList)
                        .build();
        return sessionDto;
    }
}
