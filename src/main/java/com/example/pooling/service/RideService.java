package com.example.pooling.service;

import com.example.pooling.dto.RideRequestDTO;
import com.example.pooling.entity.Ride;

import java.util.List;
public interface RideService {
    Ride createRide(RideRequestDTO dto);
    List<Ride> searchRide(String from, String to);
}
