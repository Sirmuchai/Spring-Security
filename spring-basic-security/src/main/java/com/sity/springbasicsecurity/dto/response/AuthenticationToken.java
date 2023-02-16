package com.sity.springbasicsecurity.dto.response;

import lombok.Data;

@Data
public class AuthenticationToken {
    private String token;
    private String tokenType = "Bearer ";

    public AuthenticationToken(String token) {
        this.token = token;
    }
}
