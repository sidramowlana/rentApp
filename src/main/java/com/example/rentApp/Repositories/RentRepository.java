package com.example.rentApp.Repositories;

import com.example.rentApp.Models.Equipment;
import com.example.rentApp.Models.Rent;
import com.example.rentApp.Models.User;
import com.example.rentApp.Models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepository extends JpaRepository<Rent, Integer> {
    Rent findByUserUserId(Integer userId);

    Vehicle findByVehicleVehicleId(Integer vehicleId);

    Equipment findByEquipmentEquipmentId(Integer equipmentId);

    Rent findByDateTimeFrom(String dateTimeFrom);

    Rent findByDateTimeTo(String dateTimeTo);

    Boolean existsByUserUserId(Integer userId);

    Boolean existsByVehicleVehicleId(Integer vehicleId);

    Boolean existsByEquipmentEquipmentId(Integer equipmentId);

    Boolean existsByDateTimeFrom(String dateTimeFrom);

    Boolean existsByDateTimeTo(String dateTimeTo);

}
