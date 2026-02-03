package com.example.pooling.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingRequestDTO {

    @NotNull(message = "Ride ID is required")
    private Long rideId;   // 🔥 MUST BE rideId

    @Min(value = 1, message = "At least 1 seat must be booked")
    private int seats;     // 🔥 MUST BE seats
}
