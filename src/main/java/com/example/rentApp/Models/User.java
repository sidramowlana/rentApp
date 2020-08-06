package com.example.rentApp.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String name;
    private String nic;
    private String dob;
    private String email;
    private String mobileNo;
    private String drivingLicence;
    private String username;
    private String password;
    private boolean isBlackListed;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "role", referencedColumnName = "roleId")
    private Role role;


    public User(String name, String nic, String dob, String email, String mobileNo, String drivingLicence, String username, String password, boolean isBlackListed) {
        this.name = name;
        this.nic = nic;
        this.dob = dob;
        this.email = email;
        this.mobileNo = mobileNo;
        this.drivingLicence = drivingLicence;
        this.username = username;
        this.password = password;
        this.isBlackListed = false;
    }
}
