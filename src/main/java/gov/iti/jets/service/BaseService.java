package gov.iti.jets.service;

import gov.iti.jets.entity.BaseEntity;
import gov.iti.jets.persistence.dao.BaseDao;

import java.util.List;

public interface BaseService<E extends BaseEntity, T>
{
    List<E> findAll();

    E findById(T id);

    void save(E entity);

    void update (E entity);

    List<E> saveAll(List<E> entity);

    void deleteById(T id);

    BaseDao<E, T> dao();
}
