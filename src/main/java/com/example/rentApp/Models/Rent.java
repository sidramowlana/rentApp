package com.example.rentApp.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer rentId;
    private String dateTimeFrom;
    private String dateTimeTo;

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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "vehicle_rent_equipments",
            joinColumns = @JoinColumn(name = "rent_id", referencedColumnName = "rentId"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id", referencedColumnName = "equipmentId"))
    private Set<Equipment> equipment;

    @Transient
    private Integer[] equipmentList;

    @Transient
    private String drivingLicenceImagefile;
    @Transient String utilityBillImagefile;

    public Rent(String dateTimeFrom, String dateTimeTo) {
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
    }


}