package com.example.rentApp.Repositories;

import com.example.rentApp.Models.Equipment;
import com.example.rentApp.Models.Rent;
import com.example.rentApp.Models.User;
import com.example.rentApp.Models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<Rent, Integer> {
//    Rent findByUserUserId(Integer userId);

//    List<Rent> findAllByVehicleVehicleId(Integer vehicleId);

    @Query(value = "SELECT * FROM rent r where r.vehicle = :#{#vehicle.vehicleId} and :#{#startDate} <= r.date_time_to and :#{#endDate} >= r.date_time_from",
            nativeQuery = true)
    List<Rent> findByVehicle(@Param("vehicle") Vehicle vehicle, @Param("startDate") String startDate, @Param("endDate") String endDate);

//    Rent findByVehicleVehicleId(Integer vehicleId);
//
//    Equipment findByEquipmentEquipmentId(Integer equipmentId);
//
//    Rent findByDateTimeFrom(String dateTimeFrom);
//
//    Rent findByDateTimeTo(String dateTimeTo);
//
//    Boolean existsByUserUserId(Integer userId);

    Boolean existsByVehicleVehicleId(Integer vehicleId);

//    Boolean existsByEquipmentEquipmentId(Integer equipmentId);
//
//    Boolean existsByDateTimeFrom(String dateTimeFrom);
//
//    Boolean existsByDateTimeTo(String dateTimeTo);

}


//    select all the bookings where vehicleId = 001, endDate > 2ndUser’sstartDate and startDate > 2ndUser’s endDate
//        it should return a list of booking
//        if the list size > 0 then you can display a message
//        else booking.save

//        find by vehicle, startDate <= userEndDate, endDate >= userStartDate