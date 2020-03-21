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
    private Integer quantity;
    private String description;
    private String imageUrl;

    @ManyToOne(cascade =CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="vehicle_type", referencedColumnName = "vehicleTypeId")
    private VehicleType vehicleType;

    public Vehicle(String vehicleName, String plateNo, Integer quatity, String description, String imageUrl) {
        this.vehicleName = vehicleName;
        this.plateNo = plateNo;
        this.quantity = quatity;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
