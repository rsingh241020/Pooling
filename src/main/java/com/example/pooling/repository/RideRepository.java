package com.example.pooling.repository;

import com.example.pooling.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {

    @Query("""
        SELECT r FROM Ride r
        WHERE LOWER(r.fromLocation) = LOWER(:from)
          AND LOWER(r.toLocation) = LOWER(:to)
    """)
    List<Ride> searchRideIgnoreCase(
            @Param("from") String from,
            @Param("to") String to
    );
}
