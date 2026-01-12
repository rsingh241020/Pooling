package com.example.pooling.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {

    @NotNull(message = "User ID is Required")
    private Long UserID;
    @NotNull(message="Ride ID is Required")
    private Long rideId;
    @Min(value =1 ,message = "At Least One Seat Must Be Booked")
    private int seats;

}
