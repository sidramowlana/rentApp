package com.example.rentApp.Services;

import com.example.rentApp.Models.*;
import com.example.rentApp.Repositories.*;
import com.example.rentApp.Response.MessageResponse;
import com.example.rentApp.Security.jwt.AuthEntryPointJwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private VehicleRentEquipmentsRepository vehicleRentEquipmentsRepository;
    private static final Logger logger = LoggerFactory.getLogger(RentService.class);


    @Autowired
    public RentService(RentRepository rentRepository, VehicleRepository vehicleRepository, UserRepository userRepository, EquipmentRepository equipmentRepository,
                       IdentityDocumentsRepository identityDocumentsRepository, VehicleRentEquipmentsRepository vehicleRentEquipmentsRepository) {
        this.rentRepository = rentRepository;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.equipmentRepository = equipmentRepository;
        this.identityDocumentsRepository = identityDocumentsRepository;
        this.vehicleRentEquipmentsRepository = vehicleRentEquipmentsRepository;
    }

    public ResponseEntity<?> addNewRent(Integer vehicleId, Rent newRent, HttpServletRequest httpServletRequest) {
        Optional<User> username = userRepository.findByUsername(httpServletRequest.getUserPrincipal().getName());
        long diff = newRent.getDateTimeTo().getTime() - newRent.getDateTimeFrom().getTime();
        long datediff = diff / 1000 / 60 / 60 / 24;
        long hrsdiff = diff / 1000 / 60 / 60;
        if (datediff <= 14 && hrsdiff >= 5) {
            if (rentRepository.existsByVehicleVehicleId(vehicleId)) {
                Vehicle vehicle = vehicleRepository.findById(vehicleId).get();
                List<Rent> rentList = rentRepository.findByVehicleAndDateTimeFromLessThanEqualAndDateTimeToGreaterThanEqual(vehicle, newRent.getDateTimeTo(), newRent.getDateTimeFrom());
                if (rentList.size() > 0) {
                    return ResponseEntity.ok().body(new MessageResponse("Time slots are taken already. Please select another time slot"));
                } else {
                    saveRent(newRent, username, vehicle);
                    return ResponseEntity.ok().body(new MessageResponse("Your booking request is successfully made. Please be on time and collect the booked vehicle."));
                }
            } else {
                Vehicle vehicle = vehicleRepository.findById(vehicleId).get();
                saveRent(newRent, username, vehicle);
                return ResponseEntity.ok().body(new MessageResponse("Your booking request is successfully made. Please be on time and collect the booked vehicle."));
            }
        } else {
            return ResponseEntity.ok().body(new MessageResponse("The vehcile can be rented minimum for 5 hrs and maximum for 14 days"));
        }
    }


    public ResponseEntity<?> saveRent(Rent newRent, Optional<User> username, Vehicle vehicle) {
        Integer[] list;
        List<VehicleRentEquipments> vehicleRentEquipmentList = new ArrayList<>();
        Rent rent = new Rent();
        rent.setDateTimeFrom(newRent.getDateTimeFrom());
        rent.setDateTimeTo(newRent.getDateTimeTo());
        rent.setUser(username.get());
        rent.setVehicle(vehicle);
        IdentityDocuments identityDocuments = new IdentityDocuments();
        identityDocuments.setUser(username.get());
        identityDocuments.setUtilityBillImage(newRent.getUtilityBillImagefile());
        identityDocuments.setDrivingLicenceImage(newRent.getDrivingLicenceImagefile());
        identityDocumentsRepository.save(identityDocuments);
        rent.setIdentityDocuments(identityDocuments);
        list = newRent.getList();
        for (int j = 0; j < list.length; j++) {
            Equipment equipment = equipmentRepository.findById(list[j]).get();
            VehicleRentEquipments vehicleRentEquipment = new VehicleRentEquipments();
            vehicleRentEquipment.setEquipment(equipment);
            vehicleRentEquipment.setStartDate(newRent.getDateTimeFrom());
            vehicleRentEquipment.setEndDate(newRent.getDateTimeTo());
            vehicleRentEquipmentList.add(vehicleRentEquipment);
            List<VehicleRentEquipments> equipmentList = vehicleRentEquipmentsRepository.findByEquipmentAndStartDateLessThanEqualAndEndDateGreaterThanEqual(equipment, vehicleRentEquipment.getEndDate(), vehicleRentEquipment.getStartDate());
            if (equipmentList.size() > 0) {
                return ResponseEntity.ok().body(new MessageResponse("The equipments are already booked in the selected time slot"));
            } else {
                rent.setVehicleRentEquipments(vehicleRentEquipmentList);
                rent.getVehicleRentEquipments().add(vehicleRentEquipment);
            }
        }
        rent.getVehicle().setRented(true);
        rent.setVehicleRentEquipments(vehicleRentEquipmentList);
        rentRepository.save(rent);
        return ResponseEntity.ok().body(new MessageResponse(""));
    }
}


//
//
//
//
//package com.example.rentApp.Services;
//
//        import com.example.rentApp.Models.*;
//        import com.example.rentApp.Repositories.*;
//        import com.example.rentApp.Response.MessageResponse;
//        import org.springframework.beans.factory.annotation.Autowired;
//        import org.springframework.http.ResponseEntity;
//        import org.springframework.stereotype.Service;
//
//        import javax.servlet.http.HttpServletRequest;
//        import java.text.DateFormat;
//        import java.text.ParseException;
//        import java.text.SimpleDateFormat;
//        import java.util.*;
//
//@Service
//public class RentService {
//
//    private RentRepository rentRepository;
//    private VehicleRepository vehicleRepository;
//    private UserRepository userRepository;
//    private EquipmentRepository equipmentRepository;
//    private IdentityDocumentsRepository identityDocumentsRepository;
//    private VehicleRentEquipmentsRepository vehicleRentEquipmentsRepository;
//
//
//    @Autowired
//    public RentService(RentRepository rentRepository, VehicleRepository vehicleRepository, UserRepository userRepository, EquipmentRepository equipmentRepository,
//                       IdentityDocumentsRepository identityDocumentsRepository, VehicleRentEquipmentsRepository vehicleRentEquipmentsRepository) {
//        this.rentRepository = rentRepository;
//        this.vehicleRepository = vehicleRepository;
//        this.userRepository = userRepository;
//        this.equipmentRepository = equipmentRepository;
//        this.identityDocumentsRepository = identityDocumentsRepository;
//        this.vehicleRentEquipmentsRepository = vehicleRentEquipmentsRepository;
//    }
//
//    public ResponseEntity<?> addNewRent(Integer vehicleId, Rent newRent, HttpServletRequest httpServletRequest) {
//        Optional<User> username = userRepository.findByUsername(httpServletRequest.getUserPrincipal().getName());
//        long diff = newRent.getDateTimeTo().getTime() - newRent.getDateTimeFrom().getTime();
//        long datediff = diff / 1000 / 60 / 60 / 24;
//        long hrsdiff = diff / 1000 / 60 / 60;
//        if (datediff <= 14 && hrsdiff >= 5) {
//            if (rentRepository.existsByVehicleVehicleId(vehicleId)) {
//                Vehicle vehicle = vehicleRepository.findById(vehicleId).get();
//                List<Rent> rentList = rentRepository.findByVehicleAndDateTimeFromLessThanEqualAndDateTimeToGreaterThanEqual(vehicle, newRent.getDateTimeTo(), newRent.getDateTimeFrom());
//                if (rentList.size() > 0) {
//                    return ResponseEntity.ok().body(new MessageResponse("Time slots are taken already. Please select another time slot"));
//                } else {
//                    saveRent(newRent, username, vehicle);
//                    return ResponseEntity.ok().body(new MessageResponse("Booking made Successfully"));
//                }
//            } else {
//                Vehicle vehicle = vehicleRepository.findById(vehicleId).get();
//                saveRent(newRent, username, vehicle);
//                return ResponseEntity.ok().body(new MessageResponse("Your rent request is successfully made. Please be on time and collect the rent vehicle."));
//            }
//        } else {
//            return ResponseEntity.ok().body(new MessageResponse("The vehcile can be rented minimum for 5 hrs and maximum for 14 days"));
//        }
//    }
//
//    public void saveRent(Rent newRent, Optional<User> username, Vehicle vehicle) {
//        Integer[] list;
//        List<Equipment> equipmentList = new ArrayList<>();
//        List<VehicleRentEquipments> vehicleRentEquipmentList = new ArrayList<>();
//        Rent rent = new Rent();
//        rent.setDateTimeFrom(newRent.getDateTimeFrom());
//        rent.setDateTimeTo(newRent.getDateTimeTo());
//        rent.setUser(username.get());
//        rent.setVehicle(vehicle);
//        IdentityDocuments identityDocuments = new IdentityDocuments();
//        identityDocuments.setUser(username.get());
//        identityDocuments.setUtilityBillImage(newRent.getUtilityBillImagefile());
//        identityDocuments.setDrivingLicenceImage(newRent.getDrivingLicenceImagefile());
//        identityDocumentsRepository.save(identityDocuments);
//        rent.setIdentityDocuments(identityDocuments);
//        list = newRent.getList();
//        for (int j = 0; j < list.length; j++) {
//            Equipment equipment = equipmentRepository.findById(list[j]).get();
//            VehicleRentEquipments vehicleRentEquipment = new VehicleRentEquipments();
//            vehicleRentEquipment.setEquipment(equipment);
//            vehicleRentEquipmentList.add(vehicleRentEquipment);
//            vehicleRentEquipmentsRepository.save(vehicleRentEquipment);}
//        rent.setVehicleRentEquipments(vehicleRentEquipmentList);
//        rentRepository.save(rent);
//    }
//}