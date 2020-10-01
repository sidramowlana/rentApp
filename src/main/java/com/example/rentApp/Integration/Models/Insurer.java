package com.example.rentApp.Integration.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "Insurer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Insurer implements Serializable {

private static final long serialVersionUID=1L;
    @Id
    private Integer insurerId;
    private String userId;
    private String drivingLicence;
}
