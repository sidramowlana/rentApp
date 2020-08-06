package com.example.rentApp.Response;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class JwtResponse {
        private String token;
        private String type = "Bearer";
        private Integer id;
        private String username;
        private List<String> roles;
        private Date tokenExpireTime;

    public JwtResponse(String token, Integer id, String username, List<String> roles, Date tokenExpireTime) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.tokenExpireTime = tokenExpireTime;
    }
}
