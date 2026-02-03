package com.example.pooling.dto;

import lombok.Data;

@Data
public class BookingResponseDTO {

    private Long bookingId;
    private Long rideId;
    private Long userId;
    private int seatsBooked;
    private String status;   // 🔥 REQUIRED
}
