package com.example.rentApp.Controllers;

import com.example.rentApp.Models.User;
import com.example.rentApp.Services.AuthService;
import com.example.rentApp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("api/users")
@RestController
public class UserControll {

    @Autowired
    UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/all")
    public List<User> getAllUsers() {
        return userService.allUsers();
    }

    @RequestMapping(value = "/all/{userId}")
    public ResponseEntity<?> getAUser(@PathVariable Integer userId) {
        return userService.getUserByUserId(userId);
    }

}
