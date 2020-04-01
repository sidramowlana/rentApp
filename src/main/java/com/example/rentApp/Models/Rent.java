package com.example.rentApp.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer rentId;
    private String dateTimeFrom;
    private String dateTimeTo;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "user", referencedColumnName = "userId")
    private User user;


    @OneToOne(targetEntity = IdentityDocuments.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "identity_documents_id")
    private IdentityDocuments identityDocuments;


    @OneToOne(targetEntity = Vehicle.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "vehicle_rent_equipments",
            joinColumns = @JoinColumn(name = "rent_id", referencedColumnName = "rentId"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id", referencedColumnName = "equipmentId"))
    private List<Equipment> equipment;


}