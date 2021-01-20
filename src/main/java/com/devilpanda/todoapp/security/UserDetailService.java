package com.devilpanda.todoapp.security;

import com.devilpanda.todoapp.model.User;
import com.devilpanda.todoapp.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailService implements UserDetailsService {

    private final UserService userService;

    public UserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.findByLogin(userName).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User %s not found...", userName)));
        return UserPrinciple.build(user);
    }
}
