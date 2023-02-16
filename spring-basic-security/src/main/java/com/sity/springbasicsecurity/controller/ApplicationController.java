package com.sity.springbasicsecurity.controller;

import com.sity.springbasicsecurity.dto.request.RegisterRequest;
import com.sity.springbasicsecurity.security.api.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class ApplicationController {
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/home/")
    public String home(){
        return "Spring Security Home without Authentication";
    }
    @GetMapping("/auth/getMsg")
    public String greeting() {
        return "Spring Security Example with Authentication";
    }

    @PostMapping("/home/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest){
        return authenticationService.register(registerRequest);
    }

    @PostMapping("/home/login")
    public ResponseEntity<String> login(@RequestBody RegisterRequest registerRequest){
        return authenticationService.login(registerRequest);
    }
}
