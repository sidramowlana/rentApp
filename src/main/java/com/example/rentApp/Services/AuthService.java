package com.example.rentApp.Services;

import com.example.rentApp.Models.EnumRole;
import com.example.rentApp.Models.Role;
import com.example.rentApp.Models.User;
import com.example.rentApp.Repositories.RoleRepository;
import com.example.rentApp.Repositories.UserRepository;
import com.example.rentApp.Response.JwtResponse;
import com.example.rentApp.Response.MessageResponse;
import com.example.rentApp.Security.Service.UserDetailsImpl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.example.rentApp.Security.Service.UserDetailsServiceImpl;
import com.example.rentApp.Security.jwt.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthService {

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

    String role;

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
                registerUser.getEmail(),
                registerUser.getMobileNo(),
                registerUser.getDrivingLicenceId(),
                registerUser.getUsername(),
                passwordEncoder.encode(registerUser.getPassword())
        );

        Set<String> stringRoles = registerUser.getRolesString();
        List<Role> roles = roleRepository.findAll();
        if (stringRoles == null) {
//            Role userRole = roleRepository.findByRoleName(EnumRole.ROLE_USER)
            Role userRole = roleRepository.findByRoleName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Error: Role not found"));
            user.setRole(userRole);
            ;
        } else {
            stringRoles.forEach(role -> {
                switch (role) {
                    case "admin":
//                        Role adminRole = roleRepository.findByRoleName(EnumRole.ROLE_OWNER)
                        Role adminRole = roleRepository.findByRoleName("ROLE_OWNER")
                                .orElseThrow(() -> new RuntimeException("Error: Role not found"));
                        user.setRole(adminRole);
                        break;
                    case "user":
//                        Role userRole = roleRepository.findByRoleName(EnumRole.ROLE_USER)
                        Role userRole = roleRepository.findByRoleName("ROLE_USER")
                                .orElseThrow(() -> new RuntimeException("Error: Role not found"));
                        user.setRole(userRole);
                        break;
                }
            });
        }
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

    public ResponseEntity<?> loginUserService(User loginUser) {
        if (!userRepository.existsByUsername(loginUser.getUsername())) {
            return ResponseEntity.ok("User name doesnt exist");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken((authentication));
        Date expiretime = jwtUtils.expirationTime();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        for (String r : roles) {
            this.role = r;
        }
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                this.role, expiretime));
    }
}
