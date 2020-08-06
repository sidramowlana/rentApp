package com.example.rentApp.Integration.Models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class DMV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dmvId;
    private String drivingLicence;
    private String type;
    private String offenceDate;
}
