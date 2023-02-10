package gov.iti.jets.server.service;

import gov.iti.jets.server.entity.Friends;
import gov.iti.jets.server.persistence.dao.FriendsDao;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class FriendsService {

    private FriendsDao dao;

    public FriendsService(){
        dao = new FriendsDao();
    }

    public void addFriend(String first, String second) throws RemoteException {
        Friends friends = new Friends();
        friends.setId1(first);
        friends.setId1(second);

        try {
            dao.insert(friends);
        } catch (SQLException e) {
            throw new RemoteException("Faild to add friend!!");
        }
    }
    public void removeFriend(String first, String second) throws RemoteException {
        Friends friends = new Friends();
        friends.setId1(first);
        friends.setId1(second);

        try {
            dao.deleteFriends(friends);
        } catch (SQLException e) {
            throw new RemoteException("Faild to add friend!!");
        }
    }
}
