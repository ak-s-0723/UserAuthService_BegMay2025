package org.example.userauthservice_begmay2025.services;

import org.example.userauthservice_begmay2025.dtos.UserDto;
import org.example.userauthservice_begmay2025.models.State;
import org.example.userauthservice_begmay2025.models.User;
import org.example.userauthservice_begmay2025.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User getUserById(Long id) {
        Optional<User> userOptional = userRepo.findByIdEquals(id);
        return userOptional.orElse(null);
    }


    public User getUserByEmail(String email) {
       Optional<User> userOptional = userRepo.findByEmailEquals(email);
       return userOptional.orElse(null);
    }


    public User createUer(User input) {
        Optional<User> userOptional = userRepo.findByEmailEquals(input.getEmail());
        if(userOptional.isEmpty()) return userRepo.save(input);
        return null;
    }


    public User updateUserEmail(Long userId, String newEmail) {
       Optional<User> userOptional = userRepo.findByIdEquals(userId);
       if(userOptional.isEmpty()) {
           return null;
       }else {
           User user = userOptional.get();
           user.setEmail(newEmail);
           return userRepo.save(user);
       }
    }


    //We are handling soft delete and hard delete both in single api.
    //Need to ask interviewer about requirement.
    //ToDo for Anurag -: If this is acceptable or not
    public Boolean deleteUser(String email) {
      Optional<User> optionalUser = userRepo.findByEmailEquals(email);
      if(optionalUser.isEmpty()) {
          return false;
      }else {
          User user = optionalUser.get();
          if(user.getState().equals(State.ACTIVE)) {
              user.setState(State.INACTIVE);
              userRepo.save(user);
              System.out.println("SOFT DELETE");
              return true;
          }else if (user.getState().equals(State.INACTIVE)){
            userRepo.deleteByEmail(email);
              System.out.println("HARD DELETE");
            return true;
          }
      }
        System.out.println("Something unexpected happen");
      return false;
    }
}
