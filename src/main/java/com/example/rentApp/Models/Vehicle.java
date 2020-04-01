package com.example.rentApp.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer vehicleId;
    private String vehicleName;
    private String plateNo;
    private double amount;
    private Integer quantity;
    private String description;
    private String imageUrl;
    private boolean isRented;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_type", referencedColumnName = "vehicleTypeId")
    private VehicleType vehicleType;


    public Vehicle(String vehicleName, String plateNo, double amount, Integer quatity, String description, String imageUrl, boolean isRented) {
        this.vehicleName = vehicleName;
        this.plateNo = plateNo;
        this.amount = amount;
        this.quantity = quatity;
        this.description = description;
        this.imageUrl = imageUrl;
        this.isRented = false;
    }
}
