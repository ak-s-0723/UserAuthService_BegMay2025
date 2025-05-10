package org.example.userauthservice_begmay2025.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //I need to define some object here with which i will disable spring security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().disable();
        httpSecurity.csrf().disable();
        httpSecurity.authorizeHttpRequests(authorize-> authorize.anyRequest().permitAll());
        return httpSecurity.build();
    }
}
