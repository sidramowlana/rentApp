package com.example.rentApp.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer rentId;
    private Date dateTimeFrom;
    private Date dateTimeTo;
    private boolean vehicleIsRented;

    //    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user", referencedColumnName = "userId")
    private User user;


    @OneToOne(targetEntity = IdentityDocuments.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "identity_documents_id")
    private IdentityDocuments identityDocuments;

    @OneToOne(targetEntity = Vehicle.class, fetch = FetchType.EAGER)
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

    public Rent(Date dateTimeFrom, Date dateTimeTo) {
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
    }


}
