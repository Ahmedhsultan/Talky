package gov.iti.jets.persistence.dao;

import gov.iti.jets.entity.BaseEntity;

import java.sql.SQLException;
import java.util.List;

public interface BaseDao<E extends BaseEntity, T>
{
    List<E> findAll() throws SQLException;
    E findById(T id) throws SQLException;
    Boolean deleteById(T id);
}
