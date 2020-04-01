package com.example.rentApp.Services;

import com.example.rentApp.Models.Rent;
import com.example.rentApp.Models.User;
import com.example.rentApp.Models.Vehicle;
import com.example.rentApp.Models.VehicleType;
import com.example.rentApp.Repositories.RentRepository;
import com.example.rentApp.Repositories.UserRepository;
import com.example.rentApp.Repositories.VehicleRepository;
import com.example.rentApp.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RentService {

    private RentRepository rentRepository;
    private VehicleRepository vehicleRepository;
    private UserRepository userRepository;

    @Autowired
    public RentService(RentRepository rentRepository,VehicleRepository vehicleRepository,UserRepository userRepository)
    {
        this.rentRepository = rentRepository;
        this.vehicleRepository = vehicleRepository;
        this.userRepository= userRepository;
    }

    public ResponseEntity<?> addNewRent(Rent newRent, Integer vehicleId) {
//if a vehicle is under the rent table it means isRented===true
        if(rentRepository.existsByVehicleVehicleId(vehicleId))
        {
            //look other conditions
            //check if the vehicle is rented between the time slots user have asked for
            Rent rent = rentRepository.findById(newRent.getRentId()).get();
            if(rentRepository.existsByDateTimeFrom(rent.getDateTimeFrom())){
            System.out.println("yes available");
        }

        }
        else
        {
            //get the user data
            User user = userRepository.findById(newRent.getUser().getUserId()).get();
            Vehicle vehicle = vehicleRepository.findById(newRent.getVehicle().getVehicleId()).get();
            Rent rent = new Rent();
            rent.setDateTimeFrom(newRent.getDateTimeFrom());
            rent.setDateTimeTo(newRent.getDateTimeTo());
            rent.setUser(user);
            rent.setVehicle(vehicle);
            rent.setIdentityDocuments(newRent.getIdentityDocuments());

            rentRepository.save(rent);
        }
        Rent rent = new Rent();

        rentRepository.save(rent);
        return ResponseEntity.ok().body(new MessageResponse("Your rent request is successfully made. Please be on time and collect the rent vehicle."));
    }
}
