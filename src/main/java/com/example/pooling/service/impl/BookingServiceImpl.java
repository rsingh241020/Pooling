package com.example.pooling.service.impl;

import com.example.pooling.dto.BookingRequestDTO;
import com.example.pooling.dto.BookingResponseDTO;
import com.example.pooling.entity.Booking;
import com.example.pooling.entity.Ride;
import com.example.pooling.entity.User;
import com.example.pooling.repository.BookingRepository;
import com.example.pooling.repository.RideRepository;
import com.example.pooling.repository.UserRepository;
import com.example.pooling.service.BookingService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RideRepository rideRepository;
    private final UserRepository userRepository;

    public BookingServiceImpl(
            BookingRepository bookingRepository,
            RideRepository rideRepository,
            UserRepository userRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
    }

    // ================= BOOK RIDE =================
    @Override
    @Transactional
    public BookingResponseDTO bookRide(BookingRequestDTO dto, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ride ride = rideRepository.findById(dto.getRideId())
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (ride.getDriver().getId().equals(userId)) {
            throw new RuntimeException("Driver cannot book own ride");
        }

        if (ride.getAvailableSeats() < dto.getSeats()) {
            throw new RuntimeException("Not enough seats available");
        }

        ride.setAvailableSeats(ride.getAvailableSeats() - dto.getSeats());
        rideRepository.save(ride);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRide(ride);
        booking.setSeatsBooked(dto.getSeats());

        Booking saved = bookingRepository.save(booking);

        BookingResponseDTO res = new BookingResponseDTO();
        res.setBookingId(saved.getId());
        res.setRideId(ride.getId());
        res.setUserId(user.getId());
        res.setSeatsBooked(saved.getSeatsBooked());
        res.setStatus("CONFIRMED");

        return res;
    }

    // ================= MY BOOKINGS =================
    @Override
    public List<Booking> getMyBookings(Long userId) {
        return bookingRepository.findBookingsWithRide(userId);
    }
}
