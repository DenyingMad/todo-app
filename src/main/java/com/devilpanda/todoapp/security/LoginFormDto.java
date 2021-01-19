package com.devilpanda.todoapp.security;

import lombok.Data;

@Data
public class LoginFormDto {
    private String login;
    private String password;
}
