package com.example.pooling.service;

import com.example.pooling.dto.BookingRequestDTO;
import com.example.pooling.dto.BookingResponseDTO;
import com.example.pooling.entity.Booking;

import java.util.List;

public interface BookingService {

    BookingResponseDTO bookRide(BookingRequestDTO dto, Long userId);

    // ✅ My bookings
    List<Booking> getMyBookings(Long userId);
}
