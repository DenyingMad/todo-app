package com.devilpanda.todoapp.security;

import com.devilpanda.todoapp.error.IncorrectEmailException;
import com.devilpanda.todoapp.error.UserAlreadyExistException;
import com.devilpanda.todoapp.model.User;
import com.devilpanda.todoapp.security.jwt.JwtProvider;
import com.devilpanda.todoapp.security.jwt.JwtResponse;
import com.devilpanda.todoapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class SecurityController {
    public static final Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Autowired
    UserService userService;

    public SecurityController(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> registerUser(@RequestBody LoginFormDto loginFormDto) {
        String trimmedLoginInLowerCase = loginFormDto.getLogin().trim().toLowerCase();

        User user = new User();
        user.setLogin(trimmedLoginInLowerCase);
        user.setEmail(loginFormDto.getEmail());
        user.setHashPassword(loginFormDto.getPassword());

        try {
            userService.saveUser(user);
            return authenticateUser(loginFormDto);
        } catch (UserAlreadyExistException | IncorrectEmailException e) {
            LOGGER.warn("Can't register new user -> Message: {}", e.getMessage());
            return ResponseEntity.status(418).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginFormDto loginFormDto) {
        String trimmedLoginInLowerCase = loginFormDto.getLogin().trim().toLowerCase();

        UsernamePasswordAuthenticationToken authToken
                = new UsernamePasswordAuthenticationToken(trimmedLoginInLowerCase, loginFormDto.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwtToken = jwtProvider.generateToken(auth);

        return ResponseEntity.ok(new JwtResponse(jwtToken));
    }
}
