package com.example.rentApp.Repositories;

import com.example.rentApp.Models.Equipment;
import com.example.rentApp.Models.Rent;
import com.example.rentApp.Models.User;
import com.example.rentApp.Models.Vehicle;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.JoinColumn;
import java.util.Date;
import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<Rent, Integer> {
    List<Rent> findByVehicleAndDateTimeFromLessThanEqualAndDateTimeToGreaterThanEqual(Vehicle vehicle, Date dateTimeTo, Date dateTimeFrom);

    Boolean existsByVehicleVehicleId(Integer vehicleId);

    List<Rent> findByUserUserId(Integer userId);
}
