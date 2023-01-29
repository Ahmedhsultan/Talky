package gov.iti.jets.persistence.dao;

import gov.iti.jets.entity.Invitation;
import gov.iti.jets.entity.User;
import gov.iti.jets.persistence.DBManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvitationDao implements BaseDao<Invitation, Long> {
    @Override
    public List<Invitation> findAll() {
        List<Invitation> invitations=null;
        String query = "select * from invitation;";
        Connection connection=DBManagement.getConnection();
        PreparedStatement statement =null;
        try{
            statement =connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                invitations = new ArrayList<>();
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
        finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return invitations;
    }

    @Override
    public Invitation findById(Long id) {
        Invitation invitation = null;
        String query = "select * from invitation where id = "+id+";";

        try (Connection connection = DBManagement.getConnection();PreparedStatement st = connection.prepareStatement(query)) {
            ResultSet result = st.executeQuery();

            if(result.next()) {
                 invitation = Invitation.builder()
                        .id(result.getLong("id"))
                        .senderId(result.getString("sender_id"))
                        .receiverId(result.getString("receiver_id"))
                        .createdOn(result.getDate("created_on"))
                        .status(result.getString("status"))
                        .isSeen(result.getBoolean("is_seen"))
                        .build();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return invitation;
    }


    public void save(Invitation entity) {
        String query = "insert into invitation values(?,?,?,?,?,?);";

        try (Connection connection = DBManagement.getConnection();PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getSenderId());
            statement.setString(3, entity.getReceiverId());
            statement.setDate(4, entity.getCreatedOn());
            statement.setString(5, entity.getStatus());
            statement.setBoolean(6, entity.isSeen());

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getSenderId());
            statement.setString(3, entity.getReceiverId());
            statement.setDate(4, entity.getCreatedOn());
            statement.setString(5, entity.getStatus());
            statement.setBoolean(6, entity.isSeen());

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
}
