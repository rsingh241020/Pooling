package com.example.pooling.controller;

import com.example.pooling.dto.RideRequestDTO;
import com.example.pooling.dto.RideResponseDTO;
import com.example.pooling.dto.RideSearchResponseDTO;
import com.example.pooling.entity.Ride;
import com.example.pooling.service.RideService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/rides")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    // ✅ CREATE RIDE (Driver)
    @PostMapping
    public RideResponseDTO createRide(
            HttpServletRequest request,
            @RequestBody RideRequestDTO dto
    ) {
        Long driverId = (Long) request.getAttribute("userId");

        if (driverId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return rideService.createRide(dto, driverId);
    }

    // ✅ MY RIDES (Driver)
    @GetMapping("/my")
    public List<Ride> myRides(HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");

        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return rideService.getMyRides(userId);
    }

    // ❌ OLD BOOKING API (keep only if you want)
    @PostMapping("/{rideId}/book")
    public String bookRide(@PathVariable Long rideId, HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");

        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        rideService.bookRide(rideId, userId);
        return "Ride Booked Successfully";
    }

    // ✅ SEARCH RIDE
    @GetMapping("/search")
    public List<RideSearchResponseDTO> searchRide(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {
        return rideService.searchRide(from, to, date);
    }
}
