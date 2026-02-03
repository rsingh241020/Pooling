package com.example.pooling.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
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

    @NotBlank
    @Column(name = "from_location", nullable = false)
    private String fromLocation;

    @NotBlank
    @Column(name = "to_location", nullable = false)
    private String toLocation;

    @NotNull(message = "Ride date is required")
    @Column(name = "ride_date", nullable = false)
    private LocalDate rideDate;

    @NotBlank
    @Column(name = "ride_time", nullable = false)
    private String rideTime;

    @Min(1)
    @Column(name = "total_seats", nullable = false)
    private int totalSeats;

    @Min(0)
    @Column(name = "available_seats", nullable = false)
    private int availableSeats;

    @Min(0)
    @Column(nullable = false)
    private double price;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_id", nullable = false)
    @JsonIgnoreProperties({"password"})
    private User driver;
}
