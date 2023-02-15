package com.sity.springbasicsecurity.security.api.service;

import com.sity.springbasicsecurity.dto.request.RegisterRequest;
import com.sity.springbasicsecurity.security.api.model.Role;
import com.sity.springbasicsecurity.security.api.model.UserEntity;
import com.sity.springbasicsecurity.security.api.repository.RoleRepository;
import com.sity.springbasicsecurity.security.api.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final RoleRepository roleRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<String> register(RegisterRequest registerRequest){
        if(usersRepository.existsByUsername(registerRequest.getUsername())){
            return new ResponseEntity<>("Username already Exist", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = new UserEntity();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Role role = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(role));

        usersRepository.save(user);
        return new ResponseEntity<>("User Registered successfully", HttpStatus.ACCEPTED);
    }
}
