//package com.example.rentApp.Models;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Table(name = "blacklist")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//public class BlackList {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer blacklistId;
//
//
//
//    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
//    @JoinColumn(nullable = false, name = "userId")
//    private User user;
//    private Vehicle vehicle;
//    private Date dateTimeFrom;
//    private Date dateTimeTo;
//    private double totalAmount;
//
//    public BlackList(User user, Vehicle vehicle, Date dateTimeFrom, Date dateTimeTo, double totalAmount) {
//        this.user = user;
//        this.vehicle = vehicle;
//        this.dateTimeFrom = dateTimeFrom;
//        this.dateTimeTo = dateTimeTo;
//        this.totalAmount = totalAmount;
//    }
//}
