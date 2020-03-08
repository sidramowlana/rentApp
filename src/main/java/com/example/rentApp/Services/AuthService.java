package com.example.rentApp.Services;

import com.example.rentApp.Models.EnumRole;
import com.example.rentApp.Models.Role;
import com.example.rentApp.Models.User;
import com.example.rentApp.Repositories.RoleRepository;
import com.example.rentApp.Repositories.UserRepository;
import com.example.rentApp.Response.JwtResponse;
import com.example.rentApp.Response.MessageResponse;
import com.example.rentApp.Security.Service.UserDetailsImpl;
import com.example.rentApp.Security.Service.UserDetailsServiceImpl;
import com.example.rentApp.Security.jwt.JwtUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;

    public ResponseEntity<?> registerUserService(User registerUser) {
        if (userRepository.existsByUsername(registerUser.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username already taken!"));
        }
        if (userRepository.existsByEmail(registerUser.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error: Email already taken!")));
        }

        //create a new user account after the checking
        User user = new User(
                registerUser.getName(),
                registerUser.getNic(),
                registerUser.getDob(),
                registerUser.getAge(),
                registerUser.getEmail(),
                registerUser.getMobileNo(),
                registerUser.getDrivingLicenceId(),
                registerUser.getUsername(),
                passwordEncoder.encode(registerUser.getPassword())
        );

        Set<String> stringRoles = registerUser.getRole();
        Set<Role> roles = new HashSet<>();

        if (stringRoles == null) {
            Role userRole = roleRepository.findByRoleName(EnumRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role not found"));
            roles.add(userRole);
            user.setRoles(userRole);
        } else {
            stringRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName(EnumRole.ROLE_OWNER)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found"));
                        user.setRoles(adminRole);
                        roles.add(adminRole);
                        break;
                    case "user":
                        Role userRole = roleRepository.findByRoleName(EnumRole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found"));
                        user.setRoles(userRole);
                        roles.add(userRole);
                        break;
                }
            });
        }

        user.setRoleSet(roles);
        Set<Role> r = user.getRoleSet();
        for (Role role : r) {
            System.out.println("roleis :" + role.getRoleName());
        }
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

    public ResponseEntity<?> loginUserService(User loginUser) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginUser.getUsername());
        return ResponseEntity.ok("Work");
    }

    public List<User> allUsers() {
        System.out.println(userRepository.findAll());
        return userRepository.findAll();
    }
}
