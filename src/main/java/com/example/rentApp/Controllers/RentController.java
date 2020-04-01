package com.example.rentApp.Controllers;

import com.example.rentApp.Models.Rent;
import com.example.rentApp.Models.Vehicle;
import com.example.rentApp.Services.RentService;
import com.example.rentApp.Services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class RentController {
    private RentService rentService;

    @Autowired
    public RentController(RentService rentService){
        this.rentService = rentService;
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/createRent/{{vehicleId}}")
    public ResponseEntity<?> addRent(@Valid @RequestBody Rent newRent, @PathVariable Integer vehicleId){
        return rentService.addNewRent(newRent,vehicleId);
    }
}
