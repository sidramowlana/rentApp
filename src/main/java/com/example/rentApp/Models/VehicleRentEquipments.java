package com.example.rentApp.Models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class VehicleRentEquipments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "rent", referencedColumnName = "rentId")
    private Rent rent;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "equipment", referencedColumnName = "equipmentId")
    private Equipment equipment;

    private Date startDate;
    private Date endDate;

}
