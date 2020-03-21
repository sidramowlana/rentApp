package com.example.rentApp.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class VehicleType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer vehicleTypeId;

    private String name;
    private double amount;

    public VehicleType(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }
}
