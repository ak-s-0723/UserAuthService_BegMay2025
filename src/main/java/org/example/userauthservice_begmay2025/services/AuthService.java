package org.example.userauthservice_begmay2025.services;

import io.jsonwebtoken.security.MacAlgorithm;
import org.antlr.v4.runtime.misc.Pair;
import org.example.userauthservice_begmay2025.exceptions.PasswordMismatchException;
import org.example.userauthservice_begmay2025.exceptions.UserAlreadySignedInException;
import org.example.userauthservice_begmay2025.exceptions.UserNotFoundInSystemException;
import org.example.userauthservice_begmay2025.models.User;
import org.example.userauthservice_begmay2025.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import io.jsonwebtoken.Jwts;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User signup(String email, String password) {
      Optional<User> userOptional = userRepo.findByEmailEquals(email);
      if(userOptional.isPresent()) {
         throw new UserAlreadySignedInException("Please login directly");
      }

      User user = new User();
      user.setEmail(email);
      //user.setPassword(password); //"anurag" -> "dusduwgduwgd"
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepo.save(user);
       return user;
    }

    public Pair<User,String> login(String email,String password) {
        Optional<User> userOptional = userRepo.findByEmailEquals(email);

        if(userOptional.isEmpty()) {
          throw new UserNotFoundInSystemException("Please register first !!");
        }

        String pwd = userOptional.get().getPassword();
        //if(!pwd.equals(password)) {
          if(!bCryptPasswordEncoder.matches(password,pwd)) {
          throw new
                  PasswordMismatchException("Please use correct password, otherwise reset it.");
        }


          //Token generation logic
//        String message = "{\n" +
//                "   \"email\": \"anurag@gmail.com\",\n" +
//                "   \"roles\": [\n" +
//                "      \"instructor\",\n" +
//                "      \"buddy\"\n" +
//                "   ],\n" +
//                "   \"expirationDate\": \"2ndApril2026\"\n" +
//                "}";
//          byte[] content = message.getBytes(StandardCharsets.UTF_8);

        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",userOptional.get().getId());
        Long nowInMillis = System.currentTimeMillis();
        claims.put("iat",nowInMillis);  //issued at
        claims.put("exp",nowInMillis+100000);
        claims.put("iss","scaler_uas");


          MacAlgorithm algorithm = Jwts.SIG.HS256;
          SecretKey secretKey = algorithm.key().build();

          String token  =  Jwts.builder().claims(claims).signWith(secretKey).compact();

          return new Pair<User,String>(userOptional.get(),token);

    }
}
