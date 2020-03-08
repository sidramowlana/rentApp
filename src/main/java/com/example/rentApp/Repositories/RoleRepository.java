package com.example.rentApp.Repositories;

import com.example.rentApp.Models.EnumRole;
import com.example.rentApp.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:4200")
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(String name);

}
