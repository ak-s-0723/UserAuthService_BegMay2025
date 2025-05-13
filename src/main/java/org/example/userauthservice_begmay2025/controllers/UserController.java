package org.example.userauthservice_begmay2025.controllers;

import org.example.userauthservice_begmay2025.dtos.UserDto;
import org.example.userauthservice_begmay2025.models.User;
import org.example.userauthservice_begmay2025.services.UserService;
import org.example.userauthservice_begmay2025.utils.UserMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService ;

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable  Long id) {
       User user = userService.getUserById(id);
       if(user == null) {
           throw new RuntimeException("User with Id not found");
       }

       return UserMapperUtil.from(user);
    }

    @GetMapping("/{email}")
    public UserDto getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        if(user == null) {
            throw new RuntimeException("User with Email not found");
        }

        return UserMapperUtil.from(user);
    }

    @PostMapping
    public UserDto createUer(@RequestBody User input) {
      User user = userService.createUer(input);
      return UserMapperUtil.from(user);
    }

    @PatchMapping("userId/{userId}/email/{newEmail}")
    public UserDto updateUserEmail(@PathVariable Long userId,
                                   @PathVariable String newEmail) {

        User user = userService.updateUserEmail(userId,newEmail);
        if(user == null) {
            throw new RuntimeException("User with Id not found");
        }

        return UserMapperUtil.from(user);
    }

    @DeleteMapping("/{email}")
    public Boolean deleteUser(@PathVariable String email) {
       Boolean result = userService.deleteUser(email);
       if(!result) {
            throw new RuntimeException("User not found already");
       }else {
           return true;
       }
    }
}
