package com.example.pooling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideSearchResponseDTO {

    private Long id;
    private String fromLocation;
    private String toLocation;
    private String rideDate;
    private String rideTime;
    private int availableSeats;
    private double price;

    private Long driverId;
    private String driverName;




}
