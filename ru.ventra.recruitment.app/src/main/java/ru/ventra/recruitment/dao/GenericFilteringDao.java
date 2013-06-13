package ru.ventra.recruitment.dao;

import java.util.Collection;

import javax.persistence.EntityManager;

public interface GenericFilteringDao<K, V> {
	
	public void persist(V entity);

	public V remove(K id);

	public V merge(V entity);

	public V get(K id);

	public Collection<V> getAll();

	public Collection<V> getList(int first, int size);

	public Long count();

	public Class<V> getEntityType();

	public EntityManager getEntityManager();
}
