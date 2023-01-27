package gov.iti.jets.persistence.dao;

import gov.iti.jets.entity.BaseEntity;

import java.util.List;

public interface BaseDao<E extends BaseEntity, T>
{
    List<E> findAll();
    E findById(T id);
    void save(E entity);
    void update (E entity);
    void deleteById(T id);

}
