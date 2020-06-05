//package com.example.rentApp.Services;
//
//
//import com.example.rentApp.Models.BlackList;
//import com.example.rentApp.Models.Equipment;
//import com.example.rentApp.Models.Rent;
//import com.example.rentApp.Models.User;
//import com.example.rentApp.Repositories.BlackListRepository;
//import com.example.rentApp.Repositories.EquipmentRepository;
//import com.example.rentApp.Repositories.RentRepository;
//import com.example.rentApp.Response.MessageResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.CrossOrigin;
//
//@CrossOrigin(origins = "http://localhost:4200")
//@Service
//public class BlackListService {
//    private BlackListRepository blackListRepository;
//    private RentRepository rentRepository;
//
//    @Autowired
//    public BlackListService(BlackListRepository blackListRepository, RentRepository rentRepository) {
//        this.blackListRepository = blackListRepository;
//        this.rentRepository = rentRepository;
//    }
//
//    //add a user to black list with the rent request details
//    public ResponseEntity<?> addNewBlacklister(Integer rentId) {
//        if(rentRepository.existsById(rentId)){
//            Rent rent = rentRepository.findById(rentId).get();
//            BlackList blackList = new BlackList(
//                    rent.getUser(),
//                    rent.getVehicle(),
//                    rent.getDateTimeTo(),
//                    rent.getDateTimeFrom(),
//                    rent.getTotalRentalAmount()
//                    );
//            blackListRepository.save(blackList);
//        }
//
//        return ResponseEntity.ok().body(new MessageResponse("User black listed"));
//    }
//
////    public void removeBlackLister(User user)
////    {
////        blackListRepository.existsByUserUsername(user.getUsername());
////        {
////
////        }
////        blackListRepository.findByUser(user.getUsername())
////    }
//
//
//}
