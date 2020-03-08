package com.example.rentApp.Services;

import com.example.rentApp.Models.User;
import com.example.rentApp.Repositories.UserRepository;
import com.example.rentApp.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> allUsers() {
        System.out.println(userRepository.findAll());
        return userRepository.findAll();
    }

    public ResponseEntity<?> getUserByUserId(Integer userId) {
        if (userRepository.existsById(userId)) {
            Optional<User> user = userRepository.findById(userId);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("User not found!!!"));
    }

    public User getUserByUserName(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with username: " + username));
        return user;
    }

}


