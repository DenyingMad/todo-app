package com.devilpanda.todoapp.security.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

    private String accessToken;
    private String type = "Bearer";

    public JwtResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
