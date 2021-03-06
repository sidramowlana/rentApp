package com.example.rentApp.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rentId;
    private Date dateTimeFrom;
    private Date dateTimeTo;
    private Date currentDateTime;
    private String status;
    private double totalRentalAmount;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user", referencedColumnName = "userId")
    private User user;


    @OneToOne(targetEntity = IdentityDocuments.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "identity_documents_id")
    private IdentityDocuments identityDocuments;

    @OneToOne(targetEntity = Vehicle.class, fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false, name = "vehicle")
    private Vehicle vehicle;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "rent", referencedColumnName = "rentId")
    private List<VehicleRentEquipments> VehicleRentEquipments;

    @Transient
    private List<Equipment> equipmentsList;
    @Transient
    private Integer[] list;
    @Transient
    private String drivingLicenceImagefile;
    @Transient
    String utilityBillImagefile;
}
