package gov.iti.jets.server.persistence;

import gov.iti.jets.server.entity.ChatUser;
import gov.iti.jets.server.entity.statistics.CountryStat;
import gov.iti.jets.server.entity.statistics.GenderStat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ServerDao {

    public long getNumberOfMales()
    {
        long males=0;
        String query = "select count(id) from user where  lower(gender) like 'male' ;";
        try (Connection connection = DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            //Execute the query
            ResultSet resultSet = statement.executeQuery();

            //Convert resultset to List
            if(resultSet.next()) {
                males = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return males;
    }
    public long getNumberOfUsers()
    {
        long users=0;
        String query = "select count(id) from user ";
        try (Connection connection = DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            //Execute the query
            ResultSet resultSet = statement.executeQuery();

            //Convert resultset to List
            if(resultSet.next()) {
                users = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return users;
    }
    public long getNumberOfOfflineUsers()
    {
        long offline=0;
        String query = "select count(id) from user where  lower(is_online_status) like 'offline' ;";
        try (Connection connection = DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            //Execute the query
            ResultSet resultSet = statement.executeQuery();

            //Convert resultset to List
            if(resultSet.next()) {
                offline = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return offline;
    }

    public CountryStat getCountryStats()
    {
        CountryStat countryStat = new CountryStat();
        String query = "SELECT country, count(id) from user group by country; ";
        try (Connection connection = DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            //Execute the query
            ResultSet resultSet = statement.executeQuery();

            //Convert resultset to List
            while(resultSet.next()) {
                String country = resultSet.getString(1);
                long count = resultSet.getLong(2);
                countryStat.getCountryMap().put(country,count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return countryStat;
    }
}
