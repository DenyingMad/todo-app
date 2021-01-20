package com.devilpanda.todoapp.serviceimpl;

import com.devilpanda.todoapp.error.IncorrectEmailException;
import com.devilpanda.todoapp.error.UserAlreadyExistException;
import com.devilpanda.todoapp.model.Role;
import com.devilpanda.todoapp.model.User;
import com.devilpanda.todoapp.repository.RoleRepository;
import com.devilpanda.todoapp.repository.UserRepository;
import com.devilpanda.todoapp.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static com.devilpanda.todoapp.model.RoleName.ROLE_USER;
import static java.util.Collections.singleton;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistException, IncorrectEmailException {
        if (userAlreadyExist(user))
            throw new UserAlreadyExistException(
                    String.format("Email %s or Login %s are already taken", user.getEmail(), user.getLogin()));
        if (!validateEmail(user.getEmail()))
            throw new IncorrectEmailException(
                    String.format("Email %s is not in the correct format", user.getEmail()));

        Role userRole = roleRepository.findByName(ROLE_USER).orElseThrow();
        user.setRoles(singleton(userRole));
        user.setHashPassword(passwordEncoder.encode(user.getHashPassword()));

        return userRepository.saveAndFlush(user);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User findByLoginAndPassword(String login, String hashPassword) {
        User user = userRepository.findByLogin(login).orElseThrow(() -> {
            throw new EntityNotFoundException("Can't find user with login " + login);
        });
        if (passwordEncoder.matches(hashPassword, user.getHashPassword())) {
            return user;
        }
        return null;
    }

    private boolean userAlreadyExist(User user) {
        return userRepository.findByLogin(user.getLogin()).isPresent()
                || userRepository.findByEmail(user.getEmail()).isPresent();
    }

    private boolean validateEmail(String email) {
        return email.matches("[-a-zA-Z.0-9а-яА-Я]+@[a-zA-Z.0-9а-яА-Я]+");
    }
}
