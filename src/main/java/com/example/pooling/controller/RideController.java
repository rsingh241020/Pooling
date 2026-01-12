package com.example.pooling.controller;


import com.example.pooling.dto.RideRequestDTO;
import com.example.pooling.entity.Ride;
import com.example.pooling.service.RideService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rides")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }
    @PostMapping
    public Ride createRide(@RequestBody RideRequestDTO dto) {
        return rideService.createRide(dto);
    }


    @GetMapping("/search")
    public List<Ride> searchRide(
            @RequestParam String from,
            @RequestParam String to) {
        return rideService.searchRide(from, to);
    }
}

