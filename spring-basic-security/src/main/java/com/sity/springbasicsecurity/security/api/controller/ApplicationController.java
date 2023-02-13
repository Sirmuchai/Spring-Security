package com.sity.springbasicsecurity.security.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class ApplicationController {
    @GetMapping("/getMsg")
    public String greeting(){
        return "Spring Security Example";
    }
}
