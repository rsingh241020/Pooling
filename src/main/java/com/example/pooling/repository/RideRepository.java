package com.example.pooling.repository;

import com.example.pooling.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {

    // ✅ DRIVER SIDE – MY RIDES
    List<Ride> findByDriver_Id(Long driverId);

    // ✅ SEARCH RIDES (PASSENGER SIDE)
    @Query("""
        SELECT r FROM Ride r
        JOIN FETCH r.driver d
        WHERE LOWER(r.fromLocation) = LOWER(:from)
          AND LOWER(r.toLocation) = LOWER(:to)
          AND r.rideDate = :date
    """)
    List<Ride> searchRide(
            @Param("from") String from,
            @Param("to") String to,
            @Param("date") LocalDate date
    );
}
