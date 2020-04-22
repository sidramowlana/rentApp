package com.example.rentApp.Controllers;

import com.example.rentApp.Models.Rent;
import com.example.rentApp.Models.User;
import com.example.rentApp.Services.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
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
        System.out.println(newRent);
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
    public ResponseEntity<?> extendRentByRentId(@PathVariable Integer rentId,@RequestBody Rent rent)
    {
        return rentService.extendRentByRentId(rentId,rent);
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/all")
    public List<Rent> getAll() {
        return rentService.getAll();
    }
}
