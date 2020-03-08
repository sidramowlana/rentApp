package com.example.rentApp.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    private String name;
    private String nic;
    private String dob;
    private String email;
    private String mobileNo;
    private String drivingLicenceId;
    private String username;
    private String password;

    @ManyToOne(targetEntity = Role.class, fetch = FetchType.LAZY)
    @JoinColumn(name="role_id")
    private Role role;
    @Transient
    @JsonIgnore
    private Set<String> rolesString;
    @Transient
    @JsonIgnore
    private Set<Role> roleSet;

    public User() {
    }

    public User(String name, String nic, String dob, String email, String mobileNo, String drivingLicenceId, String username, String password) {
        this.name = name;
        this.nic = nic;
        this.dob = dob;
        this.email = email;
        this.mobileNo = mobileNo;
        this.drivingLicenceId = drivingLicenceId;
        this.username = username;
        this.password = password;
    }
}
