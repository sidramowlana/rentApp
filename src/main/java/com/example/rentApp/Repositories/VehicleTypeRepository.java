package com.example.rentApp.Repositories;

import com.example.rentApp.Models.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Integer> {

    VehicleType findByName(String name);
    void deleteByName(String name);
    Boolean existsByName(String name);
}
