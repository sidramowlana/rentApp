package com.example.rentApp.Repositories;

import com.example.rentApp.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    User findByEmail(String email);

    User findByDrivingLicence(String drivingLicenceId);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByDrivingLicence(String drivingLicenceId);
}