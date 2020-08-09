package com.example.rentApp.Repositories;

import com.example.rentApp.Models.Rent;
import com.example.rentApp.Models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<Rent, Integer> {
    List<Rent> findByVehicleAndDateTimeFromLessThanEqualAndDateTimeToGreaterThanEqual(Vehicle vehicle, Date dateTimeTo, Date dateTimeFrom);

    boolean existsByVehicleVehicleId(Integer vehicleId);

    List<Rent> findByUserUserId(Integer userId);

    List<Rent> findAllByUserIsBlackListed(boolean isBlackList);
}
