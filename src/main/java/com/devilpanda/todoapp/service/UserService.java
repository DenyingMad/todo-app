package com.devilpanda.todoapp.service;

import com.devilpanda.todoapp.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User saveUser(User user);

    User findByLogin(String login);

    User findByLoginAndPassword(String login, String hashPassword);

}
