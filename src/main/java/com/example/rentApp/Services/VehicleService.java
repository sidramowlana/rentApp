package com.example.rentApp.Services;

import com.example.rentApp.Models.Equipment;
import com.example.rentApp.Models.Vehicle;
import com.example.rentApp.Models.VehicleType;
import com.example.rentApp.Repositories.VehicleRepository;
import com.example.rentApp.Repositories.VehicleTypeRepository;
import com.example.rentApp.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;
    private VehicleTypeService vehicleTypeService;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, VehicleTypeService vehicleTypeService) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleTypeService = vehicleTypeService;
    }

    public ResponseEntity<?> addNewVehicle(Vehicle newVehicle) {
//        if (vehicleRepository.existsByVehicleName(newVehicle.getVehicleName())) {
//            return ResponseEntity.badRequest().body(new MessageResponse("Vehicle already exist!!!"));
//        }
        //check with the function in the postman
        VehicleType vehicleType = vehicleTypeService.getByVehicleTypeName(newVehicle.getVehicleType().getName());
        System.out.println(vehicleType);
        Vehicle vehicle = new Vehicle(
                newVehicle.getVehicleName(),
                newVehicle.getPlateNo(),
                newVehicle.getQuantity(),
                newVehicle.getDescription(),
                newVehicle.getImageUrl()
        );
        vehicle.setVehicleType(vehicleType);
        vehicleRepository.save(vehicle);
        return ResponseEntity.ok().body(new MessageResponse("Successfully Added"));
    }

    //  get equipment by name;
    public ResponseEntity<?> getVehicleByName(String name) {
        if (!vehicleRepository.existsByVehicleName(name)) {
            return ResponseEntity.ok().body(new MessageResponse("Vehicle not available!!!"));
        } else {
            Vehicle vehicle = vehicleRepository.findByVehicleName(name);
            return ResponseEntity.ok().body(vehicle);
        }
    }

    public ResponseEntity<?> getVehicleById(Integer typeId) {
        if (vehicleRepository.existsById(typeId)) {
            Vehicle vehicle = vehicleRepository.findById(typeId).get();
            return ResponseEntity.ok(vehicle);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Vehicle not found!!!"));
    }

    public List<Vehicle> getAllVehicles () {
        return vehicleRepository.findAll();
    }

    public ResponseEntity<?> updateVehicleById(Integer id, Vehicle updateVehicle) {
        if (vehicleRepository.existsById(id)) {
            Vehicle vehicle = vehicleRepository.findById(id).get();
            vehicle.setVehicleName(updateVehicle.getVehicleName());
            vehicle.setPlateNo(updateVehicle.getPlateNo());
            vehicle.setQuantity(updateVehicle.getQuantity());
            vehicle.setDescription(updateVehicle.getDescription());
            vehicle.setImageUrl(updateVehicle.getImageUrl());
            vehicle.setVehicleType(updateVehicle.getVehicleType());
            vehicleRepository.save(vehicle);
            return ResponseEntity.ok().body(new MessageResponse("Successfully Updated"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Vehicle not available!!!"));
        }
    }
    public void deleteVehicleByName(String name) {
        if (vehicleRepository.existsByVehicleName(name)) {
            vehicleRepository.deleteByVehicleName(name);
        }
    }

    public void deleteVehicleById(Integer typeId) {
        if (vehicleRepository.existsById(typeId)) {
            vehicleRepository.deleteById(typeId);
        }
    }

    public ResponseEntity<?> getAllVehiclesByVehicleType(String vehicleType) {
        if (vehicleRepository.existsByVehicleTypeName(vehicleType)) {
            Vehicle vehicle = vehicleRepository.findByVehicleTypeName(vehicleType);
            return ResponseEntity.ok(vehicle);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("No vehicles found in this type!!!"));
    }


}
