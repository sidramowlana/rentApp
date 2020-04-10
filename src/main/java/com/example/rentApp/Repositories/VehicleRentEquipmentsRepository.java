package com.example.rentApp.Repositories;


import com.example.rentApp.Models.Equipment;
import com.example.rentApp.Models.Rent;
import com.example.rentApp.Models.Vehicle;
import com.example.rentApp.Models.VehicleRentEquipments;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

public interface VehicleRentEquipmentsRepository extends JpaRepository<VehicleRentEquipments, Integer> {
   List<VehicleRentEquipments> findByEquipmentAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Equipment equipment, Date endDate, Date startEnd);
}
