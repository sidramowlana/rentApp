package com.example.rentApp.Repositories;

import com.example.rentApp.Models.User;
import com.example.rentApp.Models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    Vehicle findByVehicleName(String vehicleName);

    Vehicle findByPlateNo(String plateNo);

    Vehicle findByVehicleId(Integer vehicleId);

    Vehicle findByVehicleTypeName(String vehicleType);

    Boolean existsByVehicleName(String vehicleName);

    Boolean existsByVehicleTypeName(String vehicleType);

    Boolean existsByPlateNo(String plateNo);

    Boolean existsByVehicleId(Integer vehicleId);

    void deleteByVehicleId(Integer VehicleId);

    void deleteByVehicleName(String name);
}
