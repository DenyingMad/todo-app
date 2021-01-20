package com.devilpanda.todoapp.serviceimpl;

import com.devilpanda.todoapp.model.User;
import com.devilpanda.todoapp.repository.RoleRepository;
import com.devilpanda.todoapp.repository.UserRepository;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

@RunWith(EasyMockRunner.class)
public class UserServiceImplTest extends EasyMockSupport {

    public static final String LOGIN = "userlogin";
    public static final String EMAIL = "user@email.com";
    public static final Optional<User> EMPTY = Optional.empty();
    @Mock
    RoleRepository roleRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @TestSubject
    UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder);
    private User user;

    @Test
    public void saveUser_success() {
        user = user(LOGIN, EMAIL, "hashedPassword");
        mockPrepareData(user, EMPTY, EMPTY);

        User savedUser = userService.saveUser(user);

        verifyAll();
        assertEquals(userFieldsToString(user), userFieldsToString(savedUser));
    }

    private void mockPrepareData(User user, Optional<User> optionalByLogin, Optional<User> optionalByEmail) {
        expect(userRepository.findByLogin(user.getLogin())).andReturn(optionalByLogin);
        expect(userRepository.findByEmail(user.getEmail())).andReturn(optionalByEmail);
        expect(passwordEncoder.encode(user.getHashPassword())).andReturn("hashedPassword");
        expect(userRepository.save(user)).andReturn(user);
        replayAll();
    }

    private User user(String login, String email, String password) {
        User user = new User();
        user.setLogin(login);
        user.setEmail(email);
        user.setHashPassword(password);
        return user;
    }

    private String userFieldsToString(User user) {
        return user.getLogin() + user.getEmail() + user.getHashPassword();
    }
}