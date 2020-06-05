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
    private VehicleTypeRepository vehicleTypeRepository;
    private VehicleTypeService vehicleTypeService;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, VehicleTypeService vehicleTypeService, VehicleTypeRepository vehicleTypeRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleTypeService = vehicleTypeService;
        this.vehicleTypeRepository = vehicleTypeRepository;
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
            System.out.println(vehicle.getVehicleId());
            return ResponseEntity.ok(vehicle);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Vehicle not found!!!"));
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public ResponseEntity<?> addNewVehicle(Vehicle newVehicle) {
//        if (vehicleRepository.existsByVehicleName(newVehicle.getVehicleName())) {
//            return ResponseEntity.badRequest().body(new MessageResponse("Vehicle already exist!!!"));
//        }
        VehicleType vehicleType = vehicleTypeService.getByVehicleTypeName(newVehicle.getVehicleType().getName());
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleName(newVehicle.getVehicleName());
        vehicle.setPlateNo(newVehicle.getPlateNo());
        vehicle.setAmount(newVehicle.getAmount());
        vehicle.setQuantity(newVehicle.getQuantity());
        vehicle.setDescription(newVehicle.getDescription());
        vehicle.setImageUrl(newVehicle.getImageUrl());
        vehicle.setRented(false);

        vehicle.setVehicleType(vehicleType);
        vehicleRepository.save(vehicle);
        System.out.println(getVehicleById(vehicle.getVehicleId()));
        return ResponseEntity.ok().body(new MessageResponse("Successfully Added"));
    }

    public ResponseEntity<?> updateVehicleById(Integer id, Vehicle updateVehicle) {
        System.out.println("before if condition");
        System.out.println("this : " + id);

        if (vehicleRepository.existsById(id)) {
            System.out.println("after if condition");

            Vehicle vehicle = vehicleRepository.findById(id).get();
            System.out.println("this : " + id);
            System.out.println("this : " + vehicle);

            VehicleType vehicleType = vehicleTypeService.getByVehicleTypeName(updateVehicle.getVehicleType().getName());
            System.out.println(vehicleType);
            System.out.println(vehicleType.getName());
            vehicle.setVehicleName(updateVehicle.getVehicleName());
            vehicle.setPlateNo(updateVehicle.getPlateNo());
            vehicle.setAmount(updateVehicle.getAmount());
            vehicle.setQuantity(updateVehicle.getQuantity());
            vehicle.setDescription(updateVehicle.getDescription());
            vehicle.setImageUrl(updateVehicle.getImageUrl());
            vehicle.setVehicleType(vehicleType);
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

    public void deleteVehicleById(Integer vehicleId) {
        if (vehicleRepository.existsById(vehicleId)) {
            System.out.println("yes id available: " + vehicleId);
            vehicleRepository.deleteById(vehicleId);
            new MessageResponse("Vehicle deleted succeffully");
//            vehicleRepository.deleteByVehicleId(vehicleId);
        } else {
            System.out.println("Vehicle Id not available");
            new MessageResponse("Vehicle Id not available");
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
