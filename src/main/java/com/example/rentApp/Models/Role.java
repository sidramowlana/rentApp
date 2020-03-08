package com.example.rentApp.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

//    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private String roleName;

    public Role() {}

    public Role(String roleName) {
        this.roleName = roleName;
    }
}