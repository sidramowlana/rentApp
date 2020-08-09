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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
