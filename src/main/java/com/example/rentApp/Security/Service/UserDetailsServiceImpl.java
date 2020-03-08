package com.example.rentApp.Security.Service;

import com.example.rentApp.Models.User;
import com.example.rentApp.Repositories.UserRepository;
import com.example.rentApp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userService;
    @Autowired
    UserDetailsServiceImpl (UserService userService){
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUserName(username);
        return UserDetailsImpl.build(user);
    }
}

