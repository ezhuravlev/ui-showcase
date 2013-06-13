package ru.ventra.recruitment.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import ru.ventra.recruitment.dao.impl.AbstractGenericFilteringDao;
import ru.ventra.recruitment.dao.UserDao;
import ru.ventra.recruitment.domain.User;
import ru.ventra.recruitment.domain.User_;

@Repository
public class UserDaoImpl extends AbstractGenericFilteringDao<Long, User> implements UserDao {
	
	public UserDaoImpl() {
		super(User.class);
	}

	@Override
	public User findUserByLogin(String login) throws NoResultException, NonUniqueResultException {
		
		EntityManager em = getEntityManager();
		
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<User> cq = cb.createQuery(User.class);

		Root<User> user = cq.from(getEntityType());
		
		cq.where(cb.equal(user.get(User_.login), login));
		
		return em.createQuery(cq).getSingleResult();
	}
}
