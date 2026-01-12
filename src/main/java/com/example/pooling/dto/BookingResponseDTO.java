package com.example.pooling.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {


    private Long bookingId;
    private Long userId;
    private Long rideId;
    private int seatsBooked;
    private String message;

}
