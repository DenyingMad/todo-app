package com.devilpanda.todoapp.security;

import com.devilpanda.todoapp.model.User;
import com.devilpanda.todoapp.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class UserDetailService implements UserDetailsService {

    private UserService userService;

    public UserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        try {
            User user = userService.findByLogin(userName);
            return UserPrinciple.build(user);
        } catch (EntityNotFoundException e) {
            throw new UsernameNotFoundException("User not found...");
        }
    }
}
