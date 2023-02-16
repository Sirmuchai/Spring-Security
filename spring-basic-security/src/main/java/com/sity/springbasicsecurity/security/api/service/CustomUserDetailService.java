package com.sity.springbasicsecurity.security.api.service;

import com.sity.springbasicsecurity.security.api.model.Role;
import com.sity.springbasicsecurity.security.api.model.UserEntity;
import com.sity.springbasicsecurity.security.api.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = usersRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        return new User(user.getUsername(),user.getPassword(), mapRoleToAuthority(user.getRoles()));
    }

    public Collection<GrantedAuthority> mapRoleToAuthority(List<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
