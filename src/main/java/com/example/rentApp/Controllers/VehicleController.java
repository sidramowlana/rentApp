package com.example.rentApp.Controllers;

import com.example.rentApp.Models.Equipment;
import com.example.rentApp.Models.Vehicle;
import com.example.rentApp.Services.EquipmentService;
import com.example.rentApp.Services.UserService;
import com.example.rentApp.Services.VehicleService;
import com.example.rentApp.Services.VehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/vehicles")
@RestController
public class VehicleController {

    private VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/createVehicle")
    public ResponseEntity<?> addVehicle(@Valid @RequestBody Vehicle newVehicle){
        return vehicleService.addNewVehicle(newVehicle);
    }

    //check if the user also needs this
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/all")
    public List<Vehicle> getAllVehicles(){
        return vehicleService.getAllVehicles();
    }

    @PreAuthorize("hasRole('ADMIN')or hasRole('USER')")
    @RequestMapping(value = "/all/{vehicleId}")
    public ResponseEntity<?> getVehicleById(@PathVariable Integer vehicleId){
        return vehicleService.getVehicleById(vehicleId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/delete/{vehicleId}")
    public void deleteVehicleById(@PathVariable Integer equipmentId){
        vehicleService.deleteVehicleById(equipmentId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/update/{vehicleId}")
    public ResponseEntity<?> updateVehicleById(@PathVariable Integer vehicleId, @RequestBody Vehicle updateVehicle){
        return vehicleService.updateVehicleById(vehicleId,updateVehicle);
    }
}
