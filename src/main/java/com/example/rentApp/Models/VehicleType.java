package com.example.rentApp.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class VehicleType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer vehicleTypeId;
    private String name;


    public VehicleType(String name) {
        this.name = name;
    }

}
