package com.sity.springbasicsecurity.security.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeHttpRequests((authz)-> authz
                        .requestMatchers("/api/v1/auth/getMsg").authenticated()
                        .requestMatchers("/api/v1/home").permitAll()
                )
                .formLogin()
                .and()
                .httpBasic();
        return http.build();
    }

    @Bean
    public UserDetails users() {
        UserDetails admin = User.builder()
                .username("admin")
                .password("pass")
                .roles("ADMIN")
                .build();
        UserDetails user = User.builder()
                .username("user")
                .password("pass")
                .roles("pass")
                .roles("USER")
                .build();
        return (UserDetails) new InMemoryUserDetailsManager(admin,user);
    }




}
