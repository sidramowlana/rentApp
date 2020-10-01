package com.example.rentApp.Integration.Repository;
import com.example.rentApp.Integration.Models.Insurer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsurerDBRepository extends JpaRepository<Insurer, Integer> {
    boolean existsByDrivingLicence(String drivingLicence);
}

