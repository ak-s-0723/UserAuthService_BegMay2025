package org.example.userauthservice_begmay2025.controllers;

import org.antlr.v4.runtime.misc.Pair;
import org.example.userauthservice_begmay2025.dtos.LoginRequestDto;
import org.example.userauthservice_begmay2025.dtos.SignupRequestDto;
import org.example.userauthservice_begmay2025.dtos.UserDto;
import org.example.userauthservice_begmay2025.dtos.ValidateTokenRequest;
import org.example.userauthservice_begmay2025.models.User;
import org.example.userauthservice_begmay2025.services.AuthService;
import org.example.userauthservice_begmay2025.utils.UserMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
            return UserMapperUtil.from(user);
        }catch (Exception exception) {
            throw exception;
        }

    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            Pair<User,String> userTokenPair = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
            UserDto userDto = UserMapperUtil.from(userTokenPair.a);
            MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
            headers.add(HttpHeaders.SET_COOKIE, userTokenPair.b);
            return new ResponseEntity<>(userDto,headers, HttpStatusCode.valueOf(201));
        }
        catch (Exception exception) {
            throw exception;
        }
    }

    @PostMapping("/validateToken")
    public ResponseEntity<String> validateToken(@RequestBody ValidateTokenRequest validateTokenRequest) {
       Boolean result = authService.validateToken(validateTokenRequest.getToken(), validateTokenRequest.getUserId());
       if(result) {
           return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
       }else {
           return new ResponseEntity<>("FAILURE", HttpStatus.UNAUTHORIZED);
       }
    }

//    private UserDto from(User user) {
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setEmail(user.getEmail());
//        return userDto;
//    }
}
