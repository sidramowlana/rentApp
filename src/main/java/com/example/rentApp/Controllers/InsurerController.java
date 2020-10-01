package com.example.rentApp.Controllers;

import com.example.rentApp.Integration.Models.Insurer;
import com.example.rentApp.Integration.Service.InsurerDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("api/insurer")
@RestController
public class InsurerController {

    private InsurerDBService insurerDBService;

    @Autowired
    public InsurerController(InsurerDBService insurerDBService) {
        this.insurerDBService = insurerDBService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/insurer-licence")
    public List<Insurer> getAllInsurerLicence() {
        return insurerDBService.getAllInsurerLicence();
    }
}
