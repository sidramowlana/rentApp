package com.example.rentApp.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class IdentityDocuments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer identityDocumentsId;
    private String drivingLicenceImage;
    private String utilityBillImage;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user", referencedColumnName = "userId")
    private User user;

}
