package com.example.pooling.service.impl;

import com.example.pooling.dto.RideRequestDTO;
import com.example.pooling.entity.Ride;
import com.example.pooling.entity.User;
import com.example.pooling.repository.RideRepository;
import com.example.pooling.repository.UserRepository;
import com.example.pooling.service.RideService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final UserRepository userRepository;

    // ✅ Constructor Injection (BEST PRACTICE)
    public RideServiceImpl(RideRepository rideRepository,
                           UserRepository userRepository) {
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
    }

    // ✅ CREATE RIDE (DTO → ENTITY)
    @Override
    public Ride createRide(RideRequestDTO dto) {

        if (dto.getDriverId() == null) {
            throw new RuntimeException("driverId is required");
        }

        // 🔹 Fetch driver from DB
        User driver = userRepository.findById(dto.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        // 🔹 Map DTO → Entity
        Ride ride = new Ride();
        ride.setFromLocation(dto.getFromLocation());
        ride.setToLocation(dto.getToLocation());
        ride.setRideDate(dto.getRideDate());
        ride.setRideTime(dto.getRideTime());
        ride.setTotalSeats(dto.getTotalSeats());
        ride.setAvailableSeats(dto.getAvailableSeats());
        ride.setPrice(dto.getPrice());
        ride.setDriver(driver);

        // 🔹 Save & return
        return rideRepository.save(ride);
    }

    // ✅ SEARCH RIDE (case-insensitive + safe)
    @Override
    public List<Ride> searchRide(String from, String to) {
        return rideRepository.searchRideIgnoreCase(
                from.trim(),
                to.trim()
        );
    }
}
