package com.example.pooling.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "rides")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Enter the location from where you start journey")
    @Column(name = "from_location", nullable = false)
    private String fromLocation;

    @NotBlank(message = "Enter the location where your journey ends")
    @Column(name = "to_location", nullable = false)
    private String toLocation;

    @NotBlank(message = "Ride date is required")
    @Column(name = "ride_date", nullable = false)
    private String rideDate;

    @NotBlank(message = "Ride time is required")
    @Column(name = "ride_time", nullable = false)
    private String rideTime;

    @Min(value = 1, message = "Total seats must be at least 1")
    @Column(name = "total_seats", nullable = false)
    private int totalSeats;

    @Min(value = 0, message = "Available seats cannot be negative")
    @Column(name = "available_seats", nullable = false)
    private int availableSeats;

    @Min(value = 0, message = "Price cannot be negative")
    @Column(nullable = false)
    private double price;

    @NotNull(message = "Driver is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private User driver;
}
