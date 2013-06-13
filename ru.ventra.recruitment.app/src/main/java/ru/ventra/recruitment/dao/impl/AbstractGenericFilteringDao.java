package ru.ventra.recruitment.dao.impl;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import ru.ventra.recruitment.dao.GenericFilteringDao;

public abstract class AbstractGenericFilteringDao<K, V> implements GenericFilteringDao<K, V> {

	private Class<V> entityType;

	@PersistenceContext
	private EntityManager em;
	
	
	
	public AbstractGenericFilteringDao(Class<V> entityType) {
		this.entityType = entityType;
	}

	@Override
	public Class<V> getEntityType() {
		return entityType;
	}
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	@Transactional
	public void persist(V entity) {
		em.persist(entity);
	}

	@Override
	public V remove(K id) {

		V entity = em.find(getEntityType(), id);

		if (null != entity) {
			em.detach(entity);
			em.remove(em.merge(entity));
		}

		return entity;
	}

	@Override
	public V merge(V entity) {
		return em.merge(entity);
	}

	@Override
	public V get(K id) {
		return em.find(getEntityType(), id);
	}

	@Override
	public Collection<V> getAll() {

		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<V> query = cb.createQuery(getEntityType());

		query.from(getEntityType());

		return em.createQuery(query)
				 .getResultList();
	}

	@Override
	public Collection<V> getList(int first, int size) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<V> query = cb.createQuery(getEntityType());

		query.from(getEntityType());
		
		return em.createQuery(query)
				 .setFirstResult(first)
				 .setMaxResults(size)
				 .getResultList();
	}

	@Override
	public Long count() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Long> query = cb.createQuery(Long.class);

		Root<V> entityRoot = query.from(getEntityType());
		
		Expression<Long> count = cb.count(entityRoot);
		
		query.select(count);
		
		return em.createQuery(query)
				 .getSingleResult();
	}
}
