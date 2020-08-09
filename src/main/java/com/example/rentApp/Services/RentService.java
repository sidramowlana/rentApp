package com.example.rentApp.Services;
import com.example.rentApp.Integration.Repository.DMVRepository;
import com.example.rentApp.Integration.Repository.InsurerDBRepository;
import com.example.rentApp.Integration.Service.DMVSchedulerService;
import com.example.rentApp.Models.*;
import com.example.rentApp.Repositories.*;
import com.example.rentApp.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@CrossOrigin(origins = "http://localhost:4200")
@Service
public class RentService {

    private RentRepository rentRepository;
    private VehicleRepository vehicleRepository;
    private UserRepository userRepository;
    private EquipmentRepository equipmentRepository;
    private IdentityDocumentsRepository identityDocumentsRepository;
    private VehicleRentEquipmentsRepository vehicleRentEquipmentsRepository;
    private JavaMailSender javaMailSender;
    private DMVSchedulerService dmvSchedulerService;
    private DMVRepository dmvRepository;
    private InsurerDBRepository insurerDBRepository;


    @Autowired
    public RentService(RentRepository rentRepository, VehicleRepository vehicleRepository, UserRepository userRepository, EquipmentRepository equipmentRepository, IdentityDocumentsRepository identityDocumentsRepository, VehicleRentEquipmentsRepository vehicleRentEquipmentsRepository, JavaMailSender javaMailSender, DMVSchedulerService dmvSchedulerService, DMVRepository dmvRepository, InsurerDBRepository insurerDBRepository) {
        this.rentRepository = rentRepository;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.equipmentRepository = equipmentRepository;
        this.identityDocumentsRepository = identityDocumentsRepository;
        this.vehicleRentEquipmentsRepository = vehicleRentEquipmentsRepository;
        this.javaMailSender = javaMailSender;
        this.dmvSchedulerService = dmvSchedulerService;
        this.dmvRepository = dmvRepository;
        this.insurerDBRepository = insurerDBRepository;
    }

    public ResponseEntity<?> addNewRent(Integer vehicleId, Rent newRent, HttpServletRequest httpServletRequest) {
        Optional<User> username = userRepository.findByUsername(httpServletRequest.getUserPrincipal().getName());
        User user = userRepository.findByUsername(username.get().getUsername()).get();
        if (!dmvRepository.existsByDrivingLicence(user.getDrivingLicence())) {        //check if user is allowed to rent with lost/stolen/suspended license;
            if (!insurerDBRepository.existsByDrivingLicence(user.getDrivingLicence())) { //check if user is a fraud
                long diff = newRent.getDateTimeTo().getTime() - newRent.getDateTimeFrom().getTime();
                long datediff = diff / 1000 / 60 / 60 / 24;
                long hrsdiff = diff / 1000 / 60 / 60;
                double totalAmount;
                if (username.get().isBlackListed() == false) { //check if user is blacklisted
                    if (datediff <= 14 && hrsdiff >= 5) {
                        if (rentRepository.existsByVehicleVehicleId(vehicleId)) {
                                Vehicle vehicle = vehicleRepository.findById(vehicleId).get();
                                List<Rent> rentList = rentRepository.findByVehicleAndDateTimeFromLessThanEqualAndDateTimeToGreaterThanEqual(vehicle, newRent.getDateTimeTo(), newRent.getDateTimeFrom());
                                totalAmount = vehicle.getAmount() * datediff;
                                if (rentList.size() > 0) {
                                    return ResponseEntity.ok().body(new MessageResponse("Time slots are taken already. Please select another time slot"));
                                } else {
                                    return saveRent(newRent, username, vehicle, totalAmount);
                                }
                        } else {
                            Vehicle vehicle = vehicleRepository.findById(vehicleId).get();
                            totalAmount = vehicle.getAmount() * datediff;
                            return saveRent(newRent, username, vehicle, totalAmount);
                        }
                    } else {
                        return ResponseEntity.ok().body(new MessageResponse("The vehicle can be rented minimum for 5 hrs and maximum for 14 days"));
                    }
                } else {
                    return ResponseEntity.ok().body(new MessageResponse("User is blacklisted"));
                }
            } else {
                System.out.println("fraud license");
                return ResponseEntity.badRequest().body(new MessageResponse("User License number is not authorized to make booking"));
            }
        } else {
            System.out.println("have to send an email to DMV");
            Date date = new Date();
            sendEmailToDMV(user, date.toString(), dmvSchedulerService.getType(user));
            return ResponseEntity.badRequest().body(new MessageResponse("Cannot allow the user with license " + user.getDrivingLicence() + " to rent"));
        }
    }

    public ResponseEntity<?> saveRent(Rent newRent, Optional<User> username, Vehicle vehicle, Double total) {
        Integer[] list;
        List<VehicleRentEquipments> vehicleRentEquipmentList = new ArrayList<>();
        Rent rent = new Rent();
        Date currentDate = new Date();
        rent.setCurrentDateTime(currentDate);
        rent.setDateTimeFrom(newRent.getDateTimeFrom());
        rent.setDateTimeTo(newRent.getDateTimeTo());
        rent.setUser(username.get());
        rent.setVehicle(vehicle);
        rent.setTotalRentalAmount(total);
        IdentityDocuments identityDocuments = new IdentityDocuments();
        identityDocuments.setUtilityBillImage(newRent.getUtilityBillImagefile());
        identityDocuments.setDrivingLicenceImage(newRent.getDrivingLicenceImagefile());
        identityDocumentsRepository.save(identityDocuments);
        rent.setIdentityDocuments(identityDocuments);
        list = newRent.getList();
        if (list != null) {
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
        }
        rent.setRentExtended(false);
        rent.getVehicle().setRented(true);
        rent.setVehicleRentEquipments(vehicleRentEquipmentList);
        rentRepository.save(rent);
            return ResponseEntity.ok().body(new MessageResponse("booking confirmed"));
    }

    public List<Rent> getAllRent(Integer userId) {
        List<Rent> rentList = rentRepository.findByUserUserId(userId);
        return rentList;
    }

    public List<Rent> getAllNotBlackListUserRents() {
        List<Rent> newRentList = rentRepository.findAllByUserIsBlackListed(false);
        return newRentList;
    }

    public List<Rent> getAllBlackListUserRents() {
        List<Rent> newRentList = rentRepository.findAllByUserIsBlackListed(true);
        return newRentList;
    }

    public ResponseEntity<?> extendRentByRentId(Integer rentId) {
        //when extending check if the vehicle is booked in the extended date
        if (rentRepository.existsById(rentId)) {
            Rent rent = rentRepository.findById(rentId).get();
            if (vehicleRentEquipmentsRepository.existsByRent(rent)) {
                VehicleRentEquipments vehicleRentEquipment = vehicleRentEquipmentsRepository.findByRent(rent);
                Date dateTimeTo = rent.getDateTimeTo();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateTimeTo);
                calendar.set(Calendar.HOUR_OF_DAY, 40); //24 hours + 1 hours to be 4 pm
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                Date newDateTime = calendar.getTime();
                rent.setDateTimeTo(newDateTime);
                rent.setRentExtended(true);
                vehicleRentEquipment.setEndDate(newDateTime);
                vehicleRentEquipmentsRepository.save(vehicleRentEquipment);
            } else {
                Date dateTimeTo = rent.getDateTimeTo();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateTimeTo);
                calendar.set(Calendar.HOUR_OF_DAY, 38); //24 hours + 1 hours to be 4 pm
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                Date newDateTime = calendar.getTime();
                rent.setDateTimeTo(newDateTime);
                rent.setRentExtended(true);
            }
            rentRepository.save(rent);
        }
        return ResponseEntity.ok().body(new MessageResponse("The booking time is extended successfully"));
    }

    public ResponseEntity<?> isTakenRentByRentId(Integer rentId) {
        if (rentRepository.existsById(rentId)) {
            Rent rent = rentRepository.findById(rentId).get();
            rent.setTaken(true);
            rentRepository.save(rent);
        }
        return ResponseEntity.ok().body(new MessageResponse("Updated"));
    }

    public Rent getAllRentById(Integer rentId) {
        Rent rent = rentRepository.findById(rentId).get();
        return rent;
    }

    public ResponseEntity<?> updateBlackListUser(Integer rentId) {
        if (rentRepository.existsById(rentId)) {
            Rent rent = rentRepository.findById(rentId).get();
            User user = userRepository.findById(rent.getUser().getUserId()).get();
            user.setBlackListed(true);
            userRepository.save(user);
            sendEmailToUserForCancellingRent(user, rent);
        }
        return ResponseEntity.ok().body(new MessageResponse("User is black listed"));
    }

    public void sendEmailToUserForCancellingRent(User user, Rent rent) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("sidra.sm18@gmail.com");
        simpleMailMessage.setSubject("Invalid licences");
        simpleMailMessage.setText("User registration number =" + user.getUserId() +
                "\\n" + "Rent id = " + rent.getRentId() +
                "\\n" + "Sorry we cancelled your booking due to failure of collecting it");
        javaMailSender.send(simpleMailMessage);
    }

    public Rent cancelRentByRentId(Integer rentId) {
        if (rentRepository.existsById(rentId)) {
            Rent rent = rentRepository.findById(rentId).get();
            rentRepository.save(rent);
            return rent;
        }
        return null;
    }

    public void sendEmailToDMV(User user, String dateTime, String type) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("sidra.sm18@gmail.com");
        simpleMailMessage.setSubject("Invalid licences");
        simpleMailMessage.setText("User registration number =" + user.getUserId() +
                 "\nUser Driving license = " + user.getDrivingLicence() +
              "\nAttempt date and time = " + dateTime +
                "\nThe license type = " + type);
        javaMailSender.send(simpleMailMessage);
    }
}