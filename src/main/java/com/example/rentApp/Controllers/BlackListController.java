//package com.example.rentApp.Controllers;
//
//import com.example.rentApp.Models.Equipment;
//import com.example.rentApp.Services.BlackListService;
//import com.example.rentApp.Services.EquipmentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//@CrossOrigin(origins = "http://localhost:4200")
//@RequestMapping("api/blacklist")
//@RestController
//public class BlackListController {
//
//    private BlackListService blackListService;
//
//    @Autowired
//    public BlackListController(BlackListService blackListService){
//        this.blackListService = blackListService;
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping(value = "/addBlackList/{rentId}")
//    public ResponseEntity<?> addBlackListUser(@PathVariable Integer rentId){
//        return blackListService.addNewBlacklister(rentId);
//    }
//}
