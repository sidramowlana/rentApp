package com.example.rentApp.Services;

import com.example.rentApp.Models.Equipment;
import com.example.rentApp.Models.VehicleType;
import com.example.rentApp.Repositories.EquipmentRepository;
import com.example.rentApp.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {
    private EquipmentRepository equipmentRepository;

    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    //add an equipment
    public ResponseEntity<?> addNewEquipment(Equipment newEquipment) {
        if (equipmentRepository.existsByEquipmentName(newEquipment.getEquipmentName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Equipment already exist!!!"));
        }
        Equipment equipment = new Equipment(
                newEquipment.getEquipmentName(),
                newEquipment.getAmount(),
                newEquipment.getDescription(),
                newEquipment.getImageUrl()
        );
        System.out.println(equipment.getEquipmentName());
        equipmentRepository.save(equipment);
        return ResponseEntity.ok().body(new MessageResponse("Successfully Added"));
    }

    //  get equipment by name;
    public ResponseEntity<?> getEquipmentByName(String name) {
        if (!equipmentRepository.existsByEquipmentName(name)) {
            return ResponseEntity.ok().body(new MessageResponse("Equipment not available!!!"));
        } else {
            Equipment equipment = equipmentRepository.findByEquipmentName(name);
            return ResponseEntity.ok().body(equipment);
        }
    }

    //  get equipment by id;
    public ResponseEntity<?> getEquipmentById(Integer id) {
        if (!equipmentRepository.existsById(id)) {
            return ResponseEntity.ok().body(new MessageResponse("Equipment not available!!!"));
        } else {
            Equipment equipment = equipmentRepository.findById(id).get();
            return ResponseEntity.ok().body(equipment);
        }
    }

    //  get all equipment
    public List<Equipment> getAllEquipments(){
        return equipmentRepository.findAll();
    }
    //  update equipment by name;
    public ResponseEntity<?> updateEquipmentById(Integer id, Equipment updateEquipment) {
        if (equipmentRepository.existsById(id)) {
            Equipment equipment = equipmentRepository.findById(id).get();
            equipment.setEquipmentName(updateEquipment.getEquipmentName());
            equipment.setAmount(updateEquipment.getAmount());
            equipment.setImageUrl(updateEquipment.getImageUrl());
            equipment.setDescription(updateEquipment.getDescription());
            equipmentRepository.save(equipment);
            return ResponseEntity.ok().body(new MessageResponse("Successfully Updated"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Equipment not available!!!"));
        }
    }

    //  delete equipment by id
    public void deleteEquipmentById(Integer id) {
        if (equipmentRepository.existsById(id)) {
            equipmentRepository.deleteById(id);
        }
    }

}
