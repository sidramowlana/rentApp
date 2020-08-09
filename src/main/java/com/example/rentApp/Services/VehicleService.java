package com.example.rentApp.Services;

import com.example.rentApp.Models.Vehicle;
import com.example.rentApp.Models.VehicleType;
import com.example.rentApp.Repositories.RentRepository;
import com.example.rentApp.Repositories.VehicleRepository;
import com.example.rentApp.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;
    private VehicleTypeService vehicleTypeService;
    private RentRepository rentRepository;


    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, VehicleTypeService vehicleTypeService, RentRepository rentRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleTypeService = vehicleTypeService;
        this.rentRepository = rentRepository;
    }


    //  get equipment by name;
    public ResponseEntity<?> getVehicleById(Integer typeId) {
        if (vehicleRepository.existsById(typeId)) {
            Vehicle vehicle = vehicleRepository.findById(typeId).get();
            return ResponseEntity.ok(vehicle);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Vehicle not found!!!"));
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public ResponseEntity<?> addNewVehicle(Vehicle newVehicle) {
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
        return ResponseEntity.ok().body(new MessageResponse("Successfully Added"));
    }

    public ResponseEntity<?> updateVehicleById(Integer id, Vehicle updateVehicle) {
        if (vehicleRepository.existsById(id)) {
            Vehicle vehicle = vehicleRepository.findById(id).get();
            VehicleType vehicleType = vehicleTypeService.getByVehicleTypeName(updateVehicle.getVehicleType().getName());
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

    public ResponseEntity<?> deleteVehicleById(Integer vehicleId) {
        if (vehicleRepository.existsById(vehicleId)) {
            if(rentRepository.existsByVehicleVehicleId(vehicleId)){
                return ResponseEntity.badRequest().body(new MessageResponse("Vehicle is been booked by your customer"));
            }else {
                vehicleRepository.deleteById(vehicleId);
                return ResponseEntity.ok().body(new MessageResponse("Vehicle deleted succeffully"));
            }
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Vehicle Id not available"));
        }
    }

}
