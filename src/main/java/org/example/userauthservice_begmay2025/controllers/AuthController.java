package org.example.userauthservice_begmay2025.controllers;

import org.example.userauthservice_begmay2025.dtos.LoginRequestDto;
import org.example.userauthservice_begmay2025.dtos.SignupRequestDto;
import org.example.userauthservice_begmay2025.dtos.UserDto;
import org.example.userauthservice_begmay2025.models.User;
import org.example.userauthservice_begmay2025.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        try {
            User user = authService.signup(signupRequestDto.getEmail(),signupRequestDto.getPassword());
            return from(user);
        }catch (Exception exception) {
            throw exception;
        }

    }

    @PostMapping("/login")
    public UserDto login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            User user  = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
            return from(user);
        }catch (Exception exception) {
            throw exception;
        }
    }

    private UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
