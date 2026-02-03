package com.example.pooling.service.impl;

import com.example.pooling.dto.RideRequestDTO;
import com.example.pooling.dto.RideResponseDTO;
import com.example.pooling.dto.RideSearchResponseDTO;
import com.example.pooling.entity.Ride;
import com.example.pooling.entity.User;
import com.example.pooling.repository.RideRepository;
import com.example.pooling.repository.UserRepository;
import com.example.pooling.service.RideService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

@Service
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final UserRepository userRepository;

    @Autowired
    private DataSource dataSource;

    public RideServiceImpl(
            RideRepository rideRepository,
            UserRepository userRepository
    ) {
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
    }

    // ================= MY RIDES (DRIVER) =================
    @Override
    public List<Ride> getMyRides(Long driverId) {
        return rideRepository.findByDriver_Id(driverId);
    }

    // 🔍 DB CHECK
    @PostConstruct
    public void checkDb() throws Exception {
        try (var conn = dataSource.getConnection()) {
            System.out.println("Connected DB URL = " + conn.getMetaData().getURL());
        }
    }

    // ================= OLD BOOK RIDE (SEAT ONLY) =================
    @Transactional
    public void bookRide(Long rideId, Long userId) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (ride.getAvailableSeats() <= 0) {
            throw new RuntimeException("No seats available");
        }

        ride.setAvailableSeats(ride.getAvailableSeats() - 1);
        rideRepository.save(ride);
    }

    // ================= CREATE RIDE =================
    @Override
    @Transactional
    public RideResponseDTO createRide(RideRequestDTO dto, Long driverId) {

        User driver = userRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        Ride ride = new Ride();
        ride.setFromLocation(dto.getFromLocation());
        ride.setToLocation(dto.getToLocation());
        ride.setRideDate(dto.getRideDate());
        ride.setRideTime(dto.getRideTime());
        ride.setTotalSeats(dto.getTotalSeats());
        ride.setAvailableSeats(dto.getTotalSeats());
        ride.setPrice(dto.getPrice());
        ride.setDriver(driver);

        Ride saved = rideRepository.save(ride);

        return RideResponseDTO.builder()
                .id(saved.getId())
                .fromLocation(saved.getFromLocation())
                .toLocation(saved.getToLocation())
                .rideDate(saved.getRideDate().toString())
                .rideTime(saved.getRideTime())
                .totalSeats(saved.getTotalSeats())
                .availableSeats(saved.getAvailableSeats())
                .price(saved.getPrice())
                .driverId(driver.getId())
                .driverName(driver.getName())
                .build();
    }

    // ================= SEARCH RIDE (PASSENGER) =================
    @Override
    @Transactional(readOnly = true)
    public List<RideSearchResponseDTO> searchRide(
            String from,
            String to,
            LocalDate date
    ) {

        from = from == null ? "" : from.trim();
        to = to == null ? "" : to.trim();

        return rideRepository.searchRide(from, to, date)
                .stream()
                .map(ride -> {
                    RideSearchResponseDTO dto = new RideSearchResponseDTO();
                    dto.setId(ride.getId());
                    dto.setFromLocation(ride.getFromLocation());
                    dto.setToLocation(ride.getToLocation());
                    dto.setRideDate(ride.getRideDate().toString());
                    dto.setRideTime(ride.getRideTime());
                    dto.setAvailableSeats(ride.getAvailableSeats());
                    dto.setPrice(ride.getPrice());
                    dto.setDriverId(ride.getDriver().getId());
                    dto.setDriverName(
                            ride.getDriver() != null
                                    ? ride.getDriver().getName()
                                    : "Unknown"
                    );
                    return dto;
                })
                .toList();
    }
}
