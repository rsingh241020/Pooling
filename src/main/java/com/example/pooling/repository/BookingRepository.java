package com.example.pooling.repository;

import com.example.pooling.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // ✅ FETCH Booking + Ride + Driver
    @Query("""
        SELECT b
        FROM Booking b
        JOIN FETCH b.ride r
        JOIN FETCH r.driver
        WHERE b.user.id = :userId
    """)
    List<Booking> findBookingsWithRide(@Param("userId") Long userId);

    // ❌ old method (unused but kept)
    List<Booking> findByUser_Id(Long userId);
}
