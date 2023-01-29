package gov.iti.jets.service;

import gov.iti.jets.entity.BaseEntity;
import gov.iti.jets.persistence.dao.BaseDao;


import java.util.List;

public class BaseServiceImpl<E extends BaseEntity, T> implements BaseService<E, T>{

    public BaseDao<E, T> dao;
    @Override
    public List<E> findAll() {
        return dao().findAll();
    }

    @Override
    public E findById(T id) {
        return dao().findById(id);
    }

    @Override
    public void save(E entity) {
         dao().save(entity);
    }

    @Override
    public void update(E entity)
    {
        dao().update(entity);
    }

    @Override
    public void deleteById(T id) {
        dao().deleteById(id);

    }

    @Override
    public BaseDao<E,T> dao() {
        return dao;
    }
}
