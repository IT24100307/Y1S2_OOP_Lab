package com.system.project1.controller;

import com.system.project1.entity.RentalBooking;
import com.system.project1.service.RentalBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class RentalBookingController {

    private final RentalBookingService bookingService;

    /**
     * Create a new rental booking
     */
    @PostMapping
    public ResponseEntity<RentalBooking> createBooking(@RequestBody RentalBooking booking) {
        RentalBooking createdBooking = bookingService.createBooking(booking);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    /**
     * Get a booking by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Long id) {
        Optional<RentalBooking> bookingOptional = bookingService.getBookingById(id);
        if (bookingOptional.isPresent()) {
            return ResponseEntity.ok(bookingOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Booking not found with id: " + id));
        }
    }

    /**
     * Get bookings by user ID
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RentalBooking>> getBookingsByUserId(@PathVariable Long userId) {
        List<RentalBooking> bookings = bookingService.getBookingsByUserId(userId);
        return ResponseEntity.ok(bookings);
    }
}
