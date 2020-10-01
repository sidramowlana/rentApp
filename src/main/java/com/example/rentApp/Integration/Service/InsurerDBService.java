package com.example.rentApp.Integration.Service;

import com.example.rentApp.Integration.Models.Insurer;
import com.example.rentApp.Integration.Repository.InsurerDBRepository;
import com.example.rentApp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsurerDBService {

    private InsurerDBRepository insurerDBRepository;

    @Autowired
    public InsurerDBService(InsurerDBRepository insurerDBRepository) {
        this.insurerDBRepository = insurerDBRepository;
    }

    public List<Insurer> getAllInsurerLicence(){
        List<Insurer> insurerList = insurerDBRepository.findAll();
        return insurerList;
    }

}
