package ru.ventra.recruitment.service;

import java.util.Collection;

import javax.persistence.EntityManager;

import ru.ventra.recruitment.dao.GenericFilteringDao;

public interface GenericFilteringService<K, V> {

    public EntityManager getEntityManager();

    public void persist(V entity);

    public V remove(K id);

    public V merge(V entity);

    public V get(K id);

    public Collection<V> getAll();

    public Collection<V> getList(int first, int size);

    public Long count();

    public GenericFilteringDao<K, V> getDao();
}
