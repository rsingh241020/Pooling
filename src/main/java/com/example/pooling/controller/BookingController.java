package com.example.pooling.controller;

import com.example.pooling.dto.BookingRequestDTO;
import com.example.pooling.dto.BookingResponseDTO;
import com.example.pooling.entity.Booking;
import com.example.pooling.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // ================= BOOK RIDE =================
    @PostMapping
    public ResponseEntity<BookingResponseDTO> bookRide(
            HttpServletRequest request,
            @Valid @RequestBody BookingRequestDTO dto
    ) {
        Long userId = (Long) request.getAttribute("userId");

        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        return ResponseEntity.ok(
                bookingService.bookRide(dto, userId)
        );
    }

    // ================= MY BOOKINGS =================
    @GetMapping("/my")
    public List<Booking> myBookings(HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");

        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        return bookingService.getMyBookings(userId);
    }
}
