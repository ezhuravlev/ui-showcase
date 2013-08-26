package ru.ventra.recruitment.service.impl;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger("org.eclipse.virgo.medic.eventlog.localized");
    
    @Autowired
    public void initDao(UserDao dao) {
        log.info("" + dao.getEntityManager().getEntityManagerFactory().getProperties());
        setDao(dao);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        User user;

        try {
            user = ((UserDao) getDao()).findUserByLogin(login);
        } catch (NoResultException e) {
            throw new UsernameNotFoundException("Username not found", e);
        }

        return user;
    }
}
