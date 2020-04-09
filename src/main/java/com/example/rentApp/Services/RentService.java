package com.example.rentApp.Services;

import com.example.rentApp.Models.*;
import com.example.rentApp.Repositories.*;
import com.example.rentApp.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RentService {

    private RentRepository rentRepository;
    private VehicleRepository vehicleRepository;
    private UserRepository userRepository;
    private EquipmentRepository equipmentRepository;
    private IdentityDocumentsRepository identityDocumentsRepository;


    @Autowired
    public RentService(RentRepository rentRepository, VehicleRepository vehicleRepository, UserRepository userRepository, EquipmentRepository equipmentRepository,
                       IdentityDocumentsRepository identityDocumentsRepository) {
        this.rentRepository = rentRepository;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.equipmentRepository = equipmentRepository;
        this.identityDocumentsRepository = identityDocumentsRepository;
    }

    public ResponseEntity<?> addNewRent(Integer vehicleId, Rent newRent, HttpServletRequest httpServletRequest) throws ParseException {
        Integer list[];
        Set<Equipment> equipmentList = new HashSet<>();
        Optional<User> username = userRepository.findByUsername(httpServletRequest.getUserPrincipal().getName());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat onlyDate = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        if (rentRepository.existsByVehicleVehicleId(vehicleId)) {
            Vehicle vehicle = vehicleRepository.findById(vehicleId).get();
            List<Rent> rentList = rentRepository.findByVehicle(vehicle, newRent.getDateTimeFrom(), newRent.getDateTimeTo());
            System.out.println(rentList);
            if (rentList.size() > 0) {
                System.out.println("Cant rent");
            } else {
                User user = userRepository.findById(username.get().getUserId()).get();
                Rent rent1 = new Rent();
                Date dateFrom = sdf.parse(newRent.getDateTimeFrom());
                String newTempFrom = onlyDate.format(dateFrom);
                Date dateTo = sdf.parse(newRent.getDateTimeTo());
                String newTempTo = onlyDate.format(dateTo);
                rent1.setDateTimeFrom(newTempFrom);
                rent1.setDateTimeTo(newTempTo);
                rent1.setUser(user);
                rent1.setVehicle(vehicle);
                list = newRent.getEquipmentList();
                for (int i = 0; i < list.length; i++) {
                    Equipment equipment = equipmentRepository.findById(list[i]).get();
                    equipmentList.add(equipment);
                    rent1.setEquipment(equipmentList);
                }
                IdentityDocuments identityDocuments = new IdentityDocuments();
                identityDocuments.setUser(user);
                identityDocuments.setUtilityBillImage(newRent.getUtilityBillImagefile());
                identityDocuments.setDrivingLicenceImage(newRent.getDrivingLicenceImagefile());
                identityDocumentsRepository.save(identityDocuments);
                rent1.setIdentityDocuments(identityDocuments);
                rentRepository.save(rent1);
                System.out.println("can");
            }
            return ResponseEntity.ok().body(new MessageResponse("yes available"));
        } else {
            Vehicle vehicle = vehicleRepository.findById(vehicleId).get();
            User user = userRepository.findById(username.get().getUserId()).get();
            Rent rent1 = new Rent();
            Date dateFrom = sdf.parse(newRent.getDateTimeFrom());
            String newTempFrom = onlyDate.format(dateFrom);
            Date dateTo = sdf.parse(newRent.getDateTimeTo());
            String newTempTo = onlyDate.format(dateTo);
            rent1.setDateTimeFrom(newTempFrom);
            rent1.setDateTimeTo(newTempTo);
            rent1.setUser(user);
            rent1.setVehicle(vehicle);
            list = newRent.getEquipmentList();
            for (int i = 0; i < list.length; i++) {
                Equipment equipment = equipmentRepository.findById(list[i]).get();
                equipmentList.add(equipment);
                rent1.setEquipment(equipmentList);
            }
            IdentityDocuments identityDocuments = new IdentityDocuments();
            identityDocuments.setUser(user);
            identityDocuments.setUtilityBillImage(newRent.getUtilityBillImagefile());
            identityDocuments.setDrivingLicenceImage(newRent.getDrivingLicenceImagefile());
            identityDocumentsRepository.save(identityDocuments);
            rent1.setIdentityDocuments(identityDocuments);
            rentRepository.save(rent1);
            return ResponseEntity.ok().body(new MessageResponse("Your rent request is successfully made. Please be on time and collect the rent vehicle."));
        }
    }
}
