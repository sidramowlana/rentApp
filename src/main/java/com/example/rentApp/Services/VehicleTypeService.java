package com.example.rentApp.Services;

import com.example.rentApp.Models.VehicleType;
import com.example.rentApp.Repositories.VehicleTypeRepository;
import com.example.rentApp.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleTypeService {

    private VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    public VehicleTypeService(VehicleTypeRepository vehicleTypeRepository) {
        this.vehicleTypeRepository = vehicleTypeRepository;
    }

    public ResponseEntity<?> addNewVehicleType(VehicleType addVehicleType){
        if(vehicleTypeRepository.existsByName(addVehicleType.getName())){
            return ResponseEntity.badRequest().body(new MessageResponse("Vehicle Type already available!"));
        }
        VehicleType vehicleType = new VehicleType(
                addVehicleType.getName());
        vehicleTypeRepository.save(vehicleType);
        return ResponseEntity.ok(vehicleType);
    }

    public List<VehicleType> getAllVehicleTypes() {
        return vehicleTypeRepository.findAll();
    }

    public VehicleType getByVehicleTypeName(String name){
           return vehicleTypeRepository.findByName(name);
    }

    public ResponseEntity<?> getVehicleTypeId(Integer typeId) {
        if (vehicleTypeRepository.existsById(typeId)) {
            VehicleType vehicleType = vehicleTypeRepository.findById(typeId).get();
            return ResponseEntity.ok(vehicleType);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Vehicle Type not found!!!"));
    }

    public ResponseEntity<?> updateVehicleType(Integer vehicleTypeId, VehicleType newVehicleType){
        if(vehicleTypeRepository.existsById(vehicleTypeId)){
            VehicleType vehicleType = vehicleTypeRepository.findById(vehicleTypeId).get();
            vehicleType.setName(newVehicleType.getName());
            vehicleTypeRepository.save(vehicleType);
            return ResponseEntity.ok().body(new MessageResponse("Successfully Added"));
        }
        else {
            return ResponseEntity.badRequest().body(new MessageResponse("Vehcile Type not available to delete"));
        }
    }

    public void deleteVehicleTypeByName(String name) {
        if (vehicleTypeRepository.existsByName(name)) {
            vehicleTypeRepository.deleteByName(name);
        }
    }

    public void deleteVehicleTypeById(Integer typeId) {
        if (vehicleTypeRepository.existsById(typeId)) {
            vehicleTypeRepository.deleteById(typeId);
        }
    }

}
