package com.devilpanda.todoapp.serviceimpl;

import com.devilpanda.todoapp.model.Role;
import com.devilpanda.todoapp.model.User;
import com.devilpanda.todoapp.repository.RoleRepository;
import com.devilpanda.todoapp.repository.UserRepository;
import com.devilpanda.todoapp.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.devilpanda.todoapp.model.RoleName.ROLE_USER;
import static java.util.Collections.singleton;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        Role userRole = roleRepository.findByName(ROLE_USER).orElseThrow();
        user.setRoles(singleton(userRole));
        user.setHashPassword(passwordEncoder.encode(user.getHashPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElseGet(User::new);
    }

    @Override
    public User findByLoginAndPassword(String login, String hashPassword) {
        User user = userRepository.findByLogin(login).orElseGet(User::new);
        if (passwordEncoder.matches(hashPassword, user.getHashPassword())) {
            return user;
        }
        return null;
    }
}
