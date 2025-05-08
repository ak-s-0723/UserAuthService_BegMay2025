package org.example.userauthservice_begmay2025.services;

import org.example.userauthservice_begmay2025.exceptions.PasswordMismatchException;
import org.example.userauthservice_begmay2025.exceptions.UserAlreadySignedInException;
import org.example.userauthservice_begmay2025.exceptions.UserNotFoundInSystemException;
import org.example.userauthservice_begmay2025.models.User;
import org.example.userauthservice_begmay2025.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    public User signup(String email, String password) {
      Optional<User> userOptional = userRepo.findByEmailEquals(email);
      if(userOptional.isPresent()) {
         throw new UserAlreadySignedInException("Please login directly");
      }

      User user = new User();
      user.setEmail(email);
      user.setPassword(password);
      userRepo.save(user);
      return user;
    }

    public User login(String email,String password) {
        Optional<User> userOptional = userRepo.findByEmailEquals(email);

        if(userOptional.isEmpty()) {
          throw new UserNotFoundInSystemException("Please register first !!");
        }

        String pwd = userOptional.get().getPassword();
        if(!pwd.equals(password)) {
          throw new
                  PasswordMismatchException("Please use correct password, otherwise reset it.");
        }

        return userOptional.get();
    }
}
