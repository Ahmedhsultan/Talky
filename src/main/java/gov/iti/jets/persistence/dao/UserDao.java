package gov.iti.jets.persistence.dao;

import gov.iti.jets.dto.BaseDto;
import gov.iti.jets.entity.User;
import gov.iti.jets.persistence.DBManagement;
import gov.iti.jets.service.BaseServiceImpl;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDao implements BaseDao<User, String> {
    @Override
    public List<User> findAll() throws SQLException {
        return null;
    }

    @Override
    public User findById(String id) throws SQLException {
        return null;
    }

    @Override
    public Boolean deleteById(String id) {
        return null;
    }

//
//    @Override
//    public List<User> findAll() {
//        return null;
//    }
//
//    @Override
//    public User findById(String id) {
//        return null;
//    }
//
//    @Override
//    public void save(User entity) {
//
//    }
//
//    @Override
//    public void update(User entity) {
//
//    }
//
//    @Override
//    public void deleteById(String id) {
//        try (Statement st = DBManagement.getInstance().getConnection().createStatement()) {
//            st.executeUpdate("delete from st where my_name='hi';");
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
}
