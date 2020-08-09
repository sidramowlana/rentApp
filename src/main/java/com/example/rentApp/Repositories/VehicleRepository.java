package com.example.rentApp.Repositories;

import com.example.rentApp.Models.User;
import com.example.rentApp.Models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    Vehicle findByVehicleName(String vehicleName);

    Vehicle findByVehicleTypeName(String vehicleType);

    boolean existsByVehicleName(String vehicleName);

    boolean existsByVehicleTypeName(String vehicleType);

    void deleteByVehicleName(String name);
}
