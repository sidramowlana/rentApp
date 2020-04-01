package com.example.rentApp.Controllers;

import com.example.rentApp.Models.User;
import com.example.rentApp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/users")
@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/all/{userId}")
    public ResponseEntity<?> getAUser(@PathVariable Integer userId) {
        return userService.getUserByUserId(userId);
    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/forgotPassword")
    public ResponseEntity<?> forgotPasswordByEmail(@RequestBody String userEmail, HttpServletRequest request) {
        return userService.forgotPasswordProcess(userEmail,request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/updatePassword/{userNameToken}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateNewPassword(@PathVariable String userNameToken, @RequestBody String newPassword)
    {
//        System.out.println("token is "+userNameToken+" new password is "+newPassword);
        System.out.println("******************************************************************************");
        return userService.updateNewPassword(userNameToken,newPassword);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/updateUser/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUserProfileById(@PathVariable Integer userId, @RequestBody User newUser)
    {
        System.out.println("user id: "+userId+"user data:"+newUser);
        return userService.updateUserById(userId,newUser);
    }
}
