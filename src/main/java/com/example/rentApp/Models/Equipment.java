package com.example.rentApp.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer equipmentId;

    private String equipmentName;
    private Integer amount;
    @Column(columnDefinition = "text")
    private String description;
    @Column(columnDefinition = "text")
    private String imageUrl;

    public Equipment(String equipmentName, Integer amount, String description, String imageUrl) {
        this.equipmentName = equipmentName;
        this.amount = amount;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
