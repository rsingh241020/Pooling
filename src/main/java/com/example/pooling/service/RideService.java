package com.example.pooling.service;

import com.example.pooling.dto.RideRequestDTO;
import com.example.pooling.dto.RideResponseDTO;
import com.example.pooling.dto.RideSearchResponseDTO;
import com.example.pooling.entity.Ride;

import java.time.LocalDate;
import java.util.List;

public interface RideService {

    RideResponseDTO createRide(RideRequestDTO dto, Long driverId);

    List<Ride> getMyRides(Long driverId);

    void bookRide(Long rideId, Long userId);

    List<RideSearchResponseDTO> searchRide(String from, String to, LocalDate date);
}
