package gov.iti.jets.persistence.dao;

import gov.iti.jets.entity.BaseEntity;

import java.sql.SQLException;
import java.util.List;

public interface BaseDao<E extends BaseEntity, T>
{
    List<E> findAll() ;
    E findById(T id) ;
    void insert(E entity);
    void update (E entity);
    Boolean deleteById(T id);
}
