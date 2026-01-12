package com.example.pooling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDTO {

    private String fromLocation;
    private String toLocation;
    private String rideDate;
    private String rideTime;
    private int totalSeats;
    private int availableSeats;
    private double price;
    private Long driverId;
}
