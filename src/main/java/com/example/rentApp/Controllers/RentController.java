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
private InsurerDBService insurerDBService;

    @Autowired
    public RentController(RentService rentService, InsurerDBService insurerDBService) {
        this.rentService = rentService;
        this.insurerDBService = insurerDBService;
    }

    @GetMapping(value = "/test/{userId}")
    public boolean checkLicense(@PathVariable Integer userId)
    {
        return insurerDBService.checkLicenseFraud(userId);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/createRent/{vehicleId}")
    public ResponseEntity<?> addRent(@PathVariable Integer vehicleId, @RequestBody Rent newRent, HttpServletRequest httpServletRequest)
    {
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

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/extendRent/{rentId}", method = RequestMethod.PUT)
    public ResponseEntity<?> extendRentByRentId(@PathVariable Integer rentId, @RequestBody Rent rent) {
        return rentService.extendRentByRentId(rentId, rent);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/rentIsTaken/{rentId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateIsTakenRentByRentId(@PathVariable Integer rentId, @RequestBody Rent rent) {
        return rentService.isTakenRentByRentId(rentId, rent);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/allNotBlacklist")
    public List<Rent> getAllNotBlackListUserRents() {
        return rentService.getAllNotBlackListUserRents();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/deleteRent/{rentId}")
    public void deleteRentByRentId(@PathVariable Integer rentId) {
        rentService.deleteRentByRentId(rentId);
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

}
