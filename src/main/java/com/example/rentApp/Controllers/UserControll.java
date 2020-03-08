package com.example.rentApp.Controllers;

import com.example.rentApp.Models.User;
import com.example.rentApp.Services.AuthService;
import com.example.rentApp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/user")
@RestController
public class UserControll {
    @Autowired
    UserService userService;


    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.allUsers();
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<?> getAUser(@PathVariable Integer userId) {
        return userService.aUser(userId);
    }
}
