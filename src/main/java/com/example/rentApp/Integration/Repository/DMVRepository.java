package com.example.rentApp.Integration.Repository;

import com.example.rentApp.Integration.Models.DMV;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DMVRepository extends JpaRepository<DMV, Integer> {
    boolean existsByDrivingLicence(String drivingLicence);

    DMV findByDrivingLicence(String drivingLicence);
}
