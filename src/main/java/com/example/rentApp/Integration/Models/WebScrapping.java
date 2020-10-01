package com.example.rentApp.Integration.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebScrapping {
    String vehcileName;
    String ratePerMonth;
    String ratePerWeek;
    String excessMilagePerDay;
}
