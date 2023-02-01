package gov.iti.jets.persistence.dao;

import gov.iti.jets.entity.BaseEntity;
import gov.iti.jets.entity.User;
import gov.iti.jets.persistence.DBManagement;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstract generic Dao class to implement CORD Queries with abstract method
 * resultsetToList wich convert from resultset to list according the concrete class implementation
 * @param <E> Entity Type
 * @param <T> ID Type
 */

public abstract class BaseDaoImpl <E extends BaseEntity, T> implements BaseDao<E, T>{

    private String tableName;

    public BaseDaoImpl(Class<E> entity){
        String[] dir  = entity.getName().split("[.]");
        tableName  = dir[dir.length-1];
    }

    @Override
    public List<E> findAll() {
        //Write select all query
        String query = "SELECT * FROM " + tableName + " ;";

        try(Connection connection =DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            //Execute the query
            ResultSet resultSet = statement.executeQuery();

            //Convert resultset to List
            List<E> list = resultSetToList(resultSet);

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public E findById(T id)  {
        //Write select quary by ID
        String query = "SELECT * FROM " + tableName + " WHERE id = "+ id +" ;";

        try(Connection connection =DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            //Execute the query
            ResultSet resultSet = statement.executeQuery();

            //Convert resultset to List
            List<E> list = resultSetToList(resultSet);
            if(list==null ||list.size()==0)
            {
                return null;
            }
            return list.get(0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean deleteById(T id) {
        //Write select query by ID
        String query = "delete FROM " + tableName + " WHERE id=" + id +";";

        try (Connection connection =DBManagement.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public abstract List<E> resultSetToList(ResultSet resultSet) throws SQLException;
}
