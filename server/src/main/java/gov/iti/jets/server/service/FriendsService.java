package gov.iti.jets.server.service;

import gov.iti.jets.common.dto.ContactDto;
import gov.iti.jets.common.dto.UserDto;
import gov.iti.jets.server.entity.Friends;
import gov.iti.jets.server.entity.User;
import gov.iti.jets.server.mapper.UserMapper;
import gov.iti.jets.server.persistence.dao.FriendsDao;
import gov.iti.jets.server.persistence.dao.UserDao;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FriendsService {

    private FriendsDao dao;
    private UserDao userDao;
    private UserMapper userMapper;

    public FriendsService(){
        dao = new FriendsDao();
        userDao = new UserDao();
        userMapper = new UserMapper();
    }

    public void addFriend(String first, String second) throws RemoteException {
        Friends friends = new Friends();
        friends.setId1(first);
        friends.setId2(second);
        try {
            dao.insert(friends);
        } catch (SQLException e) {
            throw new RemoteException("Failed to add friend!!");
        }
    }
    public void removeFriend(String first, String second) throws RemoteException {
        Friends friends = new Friends();
        friends.setId1(first);
        friends.setId1(second);

        try {
            dao.deleteFriends(friends);
        } catch (SQLException e) {
            throw new RemoteException("Failed to remove friend!!");
        }
    }
    public ArrayList<ContactDto> getListOfFriends(String id){
        //Get user contacts
        List<Friends> contactList = dao.findAllById(id);
        //Convert all contacts to set to remove duplicated user
        Set<String> idsList = contactList.stream().map(x -> new ArrayList<String>(){{
                    add(x.getId1());
                    add(x.getId2());
                }})
                .flatMap(x -> x.stream())
                .collect(Collectors.toSet());
        //Remove register user from list of contact
        idsList.remove(id);
        //Get users from id
        Set<User> userSet = idsList.stream().map(x -> userDao.findById(x)).collect(Collectors.toSet());
        //Map all contacts to dto
        ArrayList<ContactDto> userDtoList = userSet.stream().map(x -> userMapper.toContactDTO(x))
                .collect(Collectors.toCollection(ArrayList::new));

        return userDtoList;
    }
}
