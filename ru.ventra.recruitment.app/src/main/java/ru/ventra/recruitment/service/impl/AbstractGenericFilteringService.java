package ru.ventra.recruitment.service.impl;

import java.util.Collection;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

import ru.ventra.recruitment.dao.GenericFilteringDao;
import ru.ventra.recruitment.service.GenericFilteringService;

public abstract class AbstractGenericFilteringService<K, V> implements GenericFilteringService<K, V> {

    private GenericFilteringDao<K, V> dao;

    public void setDao(GenericFilteringDao<K, V> dao) {
        this.dao = dao;
    }

    @Override
    public GenericFilteringDao<K, V> getDao() {
        return dao;
    }

    public EntityManager getEntityManager() {
        return getDao().getEntityManager();
    }

    @Override
    @Transactional
    public void persist(V entity) {
        dao.persist(entity);
    }

    @Override
    @Transactional
    public V remove(K id) {
        return dao.remove(id);
    }

    @Override
    @Transactional
    public V merge(V entity) {
        return dao.merge(entity);
    }

    @Override
    @Transactional
    public V get(K id) {
        return dao.get(id);
    }

    @Override
    @Transactional
    public Collection<V> getAll() {
        return dao.getAll();
    }

    @Override
    @Transactional
    public Collection<V> getList(int first, int size) {
        return dao.getList(first, size);
    }

    @Override
    @Transactional
    public Long count() {
        return dao.count();
    }
}
