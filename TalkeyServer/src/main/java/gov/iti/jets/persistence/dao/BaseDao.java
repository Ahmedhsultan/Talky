package gov.iti.jets.persistence.dao;

import gov.iti.jets.entity.BaseEntity;

import java.sql.SQLException;
import java.util.List;

public interface BaseDao<E extends BaseEntity, T>
{
    List<E> findAll() ;
    E findById(T id);
    void insert(E entity) throws SQLException;
    void update (E entity) throws SQLException;
    Boolean deleteById(T id);
}
