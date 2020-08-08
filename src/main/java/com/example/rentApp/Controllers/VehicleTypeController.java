package com.example.rentApp.Controllers;

import com.example.rentApp.Models.VehicleType;
import com.example.rentApp.Services.VehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("api/vehicleTypes")
@RestController
public class VehicleTypeController {

    private VehicleTypeService vehicleTypeService;

    @Autowired
    public VehicleTypeController(VehicleTypeService vehicleTypeService) {
        this.vehicleTypeService = vehicleTypeService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/createVehicleType")
    public ResponseEntity<?> addVehicleType(@Valid @RequestBody VehicleType vehicleType) {
        return vehicleTypeService.addNewVehicleType(vehicleType);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/all")
    public List<VehicleType> getAllVehicleTypes() {
        return vehicleTypeService.getAllVehicleTypes();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/all/{vehicleTypeId}")
    public ResponseEntity<?> getVehicleTypeById(@PathVariable Integer vehicleTypeId) {
        return vehicleTypeService.getVehicleTypeId(vehicleTypeId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/update/{vehicleTypeId}")
    public ResponseEntity<?> updateVehicleTypeById(@PathVariable Integer vehicleTypeId, @Valid @RequestBody VehicleType newVehicleType){
        return vehicleTypeService.updateVehicleType(vehicleTypeId, newVehicleType);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/delete/{typeId}")
    public void deleteVehicleTypeById(@PathVariable Integer typeId) {
        vehicleTypeService.deleteVehicleTypeById(typeId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/delete/{typeName}")
    public void deleteVehicleTypeByName(@PathVariable String typeName) {
        vehicleTypeService.deleteVehicleTypeByName(typeName);
    }
}
