package com.devilpanda.todoapp.security;

import com.devilpanda.todoapp.model.User;
import com.devilpanda.todoapp.security.jwt.JwtProvider;
import com.devilpanda.todoapp.security.jwt.JwtResponse;
import com.devilpanda.todoapp.service.UserService;
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
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Autowired
    UserService userService;

    public SecurityController(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody LoginFormDto loginFormDto) {
        String trimmedLoginInLowerCase = loginFormDto.getLogin().trim().toLowerCase();

        User user = new User();
        user.setLogin(trimmedLoginInLowerCase);
        user.setHashPassword(loginFormDto.getPassword());

        userService.saveUser(user);

        return "Ok";
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
