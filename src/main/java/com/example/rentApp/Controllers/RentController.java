package com.example.rentApp.Controllers;

import com.example.rentApp.Integration.Service.InsurerDBService;
import com.example.rentApp.Models.Rent;
import com.example.rentApp.Models.User;
import com.example.rentApp.Services.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("api/rent")
@RestController
public class RentController {
    private RentService rentService;

    @Autowired
    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/createRent/{vehicleId}")
    public ResponseEntity<?> addRent(@PathVariable Integer vehicleId, @RequestBody Rent newRent, HttpServletRequest httpServletRequest) {
        return rentService.addNewRent(vehicleId, newRent, httpServletRequest);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/all/user/{userId}")
    public List<Rent> getAllRentsByUserId(@PathVariable Integer userId) {
        return rentService.getAllRent(userId);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/all/rent/{rentId}")
    public Rent getAllRentsByRentId(@PathVariable Integer rentId) {
        return rentService.getAllRentById(rentId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/allNotBlacklist")
    public List<Rent> getAllNotBlackListUserRents() {
        return rentService.getAllNotBlackListUserRents();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/blacklistUser/{rentId}", method = RequestMethod.PUT)
    public void updateBlackListUser(@PathVariable Integer rentId) {
        rentService.updateBlackListUser(rentId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/allBlacklist")
    public List<Rent> getAllBlackListUserRents() {
        return rentService.getAllBlackListUserRents();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/updateStatus/{rentId}", method = RequestMethod.PUT)
    public ResponseEntity<?> onUpdateStatusRentId(@PathVariable Integer rentId,@RequestBody String status) {
        return rentService.updateStatusRentId(rentId,status);
    }
}
