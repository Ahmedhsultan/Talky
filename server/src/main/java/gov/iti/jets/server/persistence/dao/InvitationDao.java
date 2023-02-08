package gov.iti.jets.server.persistence.dao;


import gov.iti.jets.server.entity.Invitation;
import gov.iti.jets.server.persistence.DBManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvitationDao extends BaseDaoImpl<Invitation, Long> {


    public InvitationDao() {
        super(Invitation.class);
    }

    public void insert(Invitation entity) {
        String query = "insert into invitation values(?,?,?,?,?,?);";

        try (Connection connection = DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            setInvitationStatement(statement, entity);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public List<Invitation> getInvitationByReceiverId(String receiverId){
        //Write select query by ID
        String query = "SELECT * FROM invitation WHERE receiver_id = "+ receiverId +" ;";

        try(Connection connection = DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            //Execute the query
            ResultSet resultSet = statement.executeQuery();

            //Convert resultset to List
            List<Invitation> list = resultSetToList(resultSet);

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(Invitation entity) {
        String query = "update  invitation set " +
                "id =?" +
                ",sender_id=?" +
                " ,receiver_id=?" +
                " ,created_on=?" +
                " ,status=?" +
                " ,is_seen=?" +
                " where id = ?";

        try (Connection connection = DBManagement.getConnection();PreparedStatement statement = connection.prepareStatement(query)) {
            setInvitationStatement(statement, entity);
            statement.setLong(7, entity.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Boolean deleteById(Long id) {
        String query = "delete from invitation where id="+id+";";
        Connection connection=DBManagement.getConnection();
        PreparedStatement statement =null;
        try  {

             statement = connection.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public List<Invitation> resultSetToList(ResultSet result){
        List<Invitation> invitations = new ArrayList<>();
        try{
            while (result.next()) {
                Invitation invitation = Invitation.builder()
                        .id(result.getLong("id"))
                        .senderId(result.getString("sender_id"))
                        .receiverId(result.getString("receiver_id"))
                        .createdOn(result.getDate("created_on"))
                        .status(result.getString("status"))
                        .isSeen(result.getBoolean("is_seen"))
                        .build();
                invitations.add(invitation);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return invitations;
    }

    private void setInvitationStatement(PreparedStatement statement, Invitation entity)
    {
        try {
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getSenderId());
            statement.setString(3, entity.getReceiverId());
            statement.setDate(4, entity.getCreatedOn());
            statement.setString(5, entity.getStatus());
            statement.setBoolean(6, entity.isSeen());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
