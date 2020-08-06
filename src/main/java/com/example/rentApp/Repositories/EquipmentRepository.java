package com.example.rentApp.Repositories;

import com.example.rentApp.Models.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
    Equipment findByEquipmentName(String name);
    Boolean existsByEquipmentName(String name);
}
