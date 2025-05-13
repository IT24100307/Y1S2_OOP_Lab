package com.system.project1.service;

import com.system.project1.entity.RentalBooking;
import com.system.project1.repository.RentalBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RentalBookingService {

    private final RentalBookingRepository rentalBookingRepository;

    /**
     * Create a new booking
     */
    @Transactional
    public RentalBooking createBooking(RentalBooking booking) {
        // Generate a unique booking number
        booking.setBookingNumber(generateBookingNumber());

        // Set initial status to PENDING
        booking.setStatus("PENDING");

        return rentalBookingRepository.save(booking);
    }

    /**
     * Get a booking by ID
     */
    public Optional<RentalBooking> getBookingById(Long id) {
        return rentalBookingRepository.findById(id);
    }

    /**
     * Get bookings by user ID
     */
    public List<RentalBooking> getBookingsByUserId(Long userId) {
        return rentalBookingRepository.findByUserId(userId);
    }

    /**
     * Generate a unique booking number
     */
    private String generateBookingNumber() {
        return "BK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
