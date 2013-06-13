package ru.ventra.recruitment.dao;

import ru.ventra.recruitment.domain.User;

public interface UserDao extends GenericFilteringDao<Long, User> {

    public User findUserByLogin(String login);
}
