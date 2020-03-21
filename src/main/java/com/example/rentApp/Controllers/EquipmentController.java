package com.example.rentApp.Controllers;

import com.example.rentApp.Models.Equipment;
import com.example.rentApp.Services.EquipmentService;
import com.example.rentApp.Services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/equipment")
@RestController
public class EquipmentController {
    private EquipmentService equipmentService;

    @Autowired
    public EquipmentController(EquipmentService equipmentService){
        this.equipmentService = equipmentService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/createEquipment")
    public ResponseEntity<?> addEquipment(@Valid @RequestBody Equipment newequipment){
        return equipmentService.addNewEquipment(newequipment);
    }

    //check if the user also needs this
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/all")
    public List<Equipment> getAllEquipments(){
        return equipmentService.getAllEquipments();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/all/{equipmentId}")
    public ResponseEntity<?> getEquipmentById(@PathVariable Integer equipmentId){
        return equipmentService.getEquipmentById(equipmentId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/delete/{equipmentId}")
    public void deleteEquipmentById(@PathVariable Integer equipmentId){
        equipmentService.deleteEquipmentById(equipmentId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/update/{equipmentId}")
    public ResponseEntity<?> updateEquipmentById(@PathVariable Integer equipmentId, @RequestBody Equipment updateEquipment){
        return equipmentService.updateEquipmentById(equipmentId,updateEquipment);
    }

}

