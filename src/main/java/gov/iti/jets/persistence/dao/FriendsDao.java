package gov.iti.jets.persistence.dao;

import gov.iti.jets.entity.BaseEntity;
import gov.iti.jets.entity.ChatUser;
import gov.iti.jets.entity.Friends;
import gov.iti.jets.entity.User;
import gov.iti.jets.persistence.DBManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendsDao extends BaseDaoImpl<Friends,String>{
    public FriendsDao() {super(Friends.class);}

    public List<Friends> findAllById(String id)  {
        //Write select query by ID
        String query = "SELECT * FROM Friends WHERE id1 = "+ id +" || id2 = " + id + " ;";

        try(Connection connection = DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            //Execute the query
            ResultSet resultSet = statement.executeQuery();

            //Convert resultset to List
            List<Friends> list = resultSetToList(resultSet);

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Deprecated
    public Friends findById(String id)  {
        return  null;
    }

    @Deprecated
    public Boolean deleteById(String id) {
        return  null;
    }

    @Override
    public void insert(Friends entity) {

    }

    @Override
    public void update(Friends entity) {

    }

    @Override
    public List<Friends> resultSetToList(ResultSet resultSet) throws SQLException {
        List<Friends> friendsList = new ArrayList<>();
        try{
            while (resultSet.next()) {
                Friends friends = Friends.builder()
                        .id1(resultSet.getString("id1"))
                        .id2(resultSet.getString("id2"))
                        .build();
                friendsList.add(friends);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return friendsList;
    }
}
