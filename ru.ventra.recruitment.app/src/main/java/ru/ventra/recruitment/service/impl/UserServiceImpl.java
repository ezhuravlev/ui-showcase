package ru.ventra.recruitment.service.impl;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.ventra.recruitment.dao.UserDao;
import ru.ventra.recruitment.domain.User;
import ru.ventra.recruitment.service.UserService;

@Service
public class UserServiceImpl extends AbstractGenericFilteringService<Long, User> implements UserService {

	@Autowired
	public void initDao(UserDao dao) {
		setDao(dao);
	}
	
	@Override
	@Transactional 
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		
		User user;
		
		try {
			user = ((UserDao)getDao()).findUserByLogin(login);
		} catch(NoResultException e) {
			throw new UsernameNotFoundException("Username not found", e);
		}
		
		return user;
	}
}
