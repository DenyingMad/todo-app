package com.devilpanda.todoapp.service;

import com.devilpanda.todoapp.error.UserAlreadyExistException;
import com.devilpanda.todoapp.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    User saveUser(User user) throws UserAlreadyExistException;

    Optional<User> findByLogin(String login);

    User findByLoginAndPassword(String login, String hashPassword);

}
