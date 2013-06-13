package ru.ventra.recruitment.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import ru.ventra.recruitment.domain.User;

public interface UserService extends GenericFilteringService<Long, User>, UserDetailsService {

}