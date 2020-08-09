package com.example.rentApp.Repositories;

import com.example.rentApp.Models.Equipment;
import com.example.rentApp.Models.Rent;
import com.example.rentApp.Models.VehicleRentEquipments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VehicleRentEquipmentsRepository extends JpaRepository<VehicleRentEquipments, Integer> {
    List<VehicleRentEquipments> findByEquipmentAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Equipment equipment, Date endDate, Date startEnd);

    VehicleRentEquipments findByRent(Rent rentId);

    boolean existsByEquipment(Equipment equipment);

    boolean existsByRent(Rent rentId);

}
