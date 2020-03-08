package com.example.rentApp.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Integer id;
    private String username;
    private String roles;
    private Date tokenExpireTime;

    public JwtResponse(String token, Integer id, String username, String roles, Date tokenExpireTime) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.tokenExpireTime = tokenExpireTime;
    }
}
