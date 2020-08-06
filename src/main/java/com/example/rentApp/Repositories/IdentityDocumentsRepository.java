package com.example.rentApp.Repositories;

import com.example.rentApp.Models.Equipment;
import com.example.rentApp.Models.IdentityDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentityDocumentsRepository extends JpaRepository<IdentityDocuments, Integer> {
    IdentityDocuments findByDrivingLicenceImage(String drivingLicenceImage);

    IdentityDocuments findByUtilityBillImage(String utilityBillImage);

    Boolean existsByDrivingLicenceImage(String drivingLicenceImage);

    Boolean existsByUtilityBillImage(String utilityBillImage);
}
