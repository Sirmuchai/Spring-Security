package com.sity.springbasicsecurity.security.api.service;

import com.sity.springbasicsecurity.dto.request.RegisterRequest;
import com.sity.springbasicsecurity.dto.response.AuthenticationToken;
import com.sity.springbasicsecurity.security.api.config.JwtUtil;
import com.sity.springbasicsecurity.security.api.model.Role;
import com.sity.springbasicsecurity.security.api.model.UserEntity;
import com.sity.springbasicsecurity.security.api.repository.RoleRepository;
import com.sity.springbasicsecurity.security.api.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthenticationService {

    private RoleRepository roleRepository;
    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;
    private  AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    @Autowired
    public AuthenticationService(RoleRepository roleRepository, UsersRepository usersRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.roleRepository = roleRepository;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<String> register(RegisterRequest registerRequest) {
        if (usersRepository.existsByUsername(registerRequest.getUsername())) {
            return new ResponseEntity<>("User Already Exist", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = new UserEntity();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Role role = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(role));

        usersRepository.save(user);
        return new ResponseEntity<>("User Registered successfully", HttpStatus.ACCEPTED);
    }

    public ResponseEntity<AuthenticationToken> login(RegisterRequest registerRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerRequest.getUsername(),
                        registerRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateToken(authentication);
        return new ResponseEntity<>(new AuthenticationToken(token), HttpStatus.OK);
    }
}