package gov.iti.jets.persistence.dao;

import gov.iti.jets.entity.ChatUser;
import gov.iti.jets.entity.Notification;
import gov.iti.jets.persistence.DBManagement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDao extends BaseDaoImpl<Notification,Integer>{
    public NotificationDao() {super(Notification.class);}

    public List<Notification> getNotificationsByUserId(String userId){
        //Write select query by ID
        String query = "SELECT * FROM notification WHERE receiver_id = "+ userId +" ;";

        try(Connection connection = DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            //Execute the query
            ResultSet resultSet = statement.executeQuery();

            //Convert resultset to List
            List<Notification> list = resultSetToList(resultSet);

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void insert(Notification entity) throws SQLException {
        String query = "insert into notification "+
                "(sender_id,receiver_id,type,created_on,status,is_seen) "+
                "values(?,?,?,?,?,?);";

        try (Connection connection = DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            setStatement(statement,entity);
            statement.executeUpdate();
        }catch (SQLException ex){
            ex.printStackTrace();
            throw new SQLException();
        }
    }

    @Override
    public void update(Notification entity) throws SQLException {

    }

    private void setStatement(PreparedStatement statement, Notification entity)throws SQLException
    {
        statement.setInt(1, entity.getSender_id());
        statement.setInt(2, entity.getReceiver_id());
        statement.setString(3, entity.getType());
        statement.setDate(4, entity.getCreated_on());
        statement.setString(5, entity.getStatus());
        statement.setBoolean(6, entity.is_seen());
    }

    @Override
    public List<Notification> resultSetToList(ResultSet resultSet) throws SQLException {
        List<Notification> notificationList = new ArrayList<>();
        try{
            while (resultSet.next()) {
                Notification notification = Notification.builder()
                        .id(resultSet.getInt("id"))
                        .sender_id(resultSet.getInt("sender_id"))
                        .receiver_id(resultSet.getInt("receiver_id"))
                        .type(resultSet.getString("type"))
                        .status(resultSet.getString("status"))
                        .is_seen(resultSet.getBoolean("is_seen"))
                        .created_on(resultSet.getDate("created_on"))
                        .build();
                notificationList.add(notification);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return notificationList;
    }
}
