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

    public BaseDaoImpl(E Entity){
        String[] dir  = Entity.getClass().getName().split("[.]");
        tableName  = dir[dir.length-1];
    }

    @Override
    public List<E> findAll() {
        //Write select all quary
        String query = "SELECT * FROM " + tableName + " ;";

        try(PreparedStatement statement = DBManagement.getConnection().prepareStatement(query)) {
            //Excute the query
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
    public E findById(T id) throws SQLException {
        //Write select quary by ID
        String query = "SELECT * FROM " + tableName + " WHERE id = "+ id +" ;";

        try(PreparedStatement statement = DBManagement.getConnection().prepareStatement(query)) {
            //Excute the query
            ResultSet resultSet = statement.executeQuery();

            //Convert resultset to List
            List<E> list = resultSetToList(resultSet);

            return list.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean deleteById(T id) {
        //Write select quary by ID
        String query = "delete FROM " + tableName + " WHERE id=" + id +";";

        try (PreparedStatement statement = DBManagement.getConnection().prepareStatement(query)) {
            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public abstract List<E> resultSetToList(ResultSet resultSet) throws SQLException;
}
