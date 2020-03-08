package com.example.rentApp.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EnumRole roleName;

    public Role() {}

    public Role(EnumRole roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public EnumRole getRoleName() {
        return roleName;
    }

    public void setRoleName(EnumRole roleName) {
        this.roleName = roleName;
    }
}