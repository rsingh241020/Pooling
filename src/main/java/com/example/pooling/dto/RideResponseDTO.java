package com.example.pooling.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideResponseDTO {

    private Long id;
    private String fromLocation;
    private String toLocation;
    private String rideDate;
    private String rideTime;
    private int totalSeats;
    private int availableSeats;
    private double price;

    private Long driverId;
    private String driverName;
}
