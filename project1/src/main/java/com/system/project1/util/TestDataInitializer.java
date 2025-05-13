package com.system.project1.util;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.system.project1.entity.RentalBooking;
import com.system.project1.repository.RentalBookingRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * This class creates sample data for testing the payment system.
 * It's only activated with the "dev" profile.
 */
@Configuration
@Slf4j
@Profile("dev")
public class TestDataInitializer {

    @Bean
    public CommandLineRunner initData(RentalBookingRepository bookingRepository) {
        return args -> {
            log.info("Initializing test data...");
            
            // Create a few sample bookings
            RentalBooking booking1 = new RentalBooking();
            booking1.setVehicleId(1L);
            booking1.setUserId(1L);
            booking1.setBookingNumber("BK-12345678");
            booking1.setStartDate(new Date());
            // Set end date to 5 days from now
            booking1.setEndDate(new Date(System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000));
            booking1.setTotalAmount(new BigDecimal("448.00"));
            booking1.setStatus("PENDING");
            
            bookingRepository.save(booking1);
            
            RentalBooking booking2 = new RentalBooking();
            booking2.setVehicleId(2L);
            booking2.setUserId(1L);
            booking2.setBookingNumber("BK-87654321");
            booking2.setStartDate(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000));
            booking2.setEndDate(new Date(System.currentTimeMillis() + 10 * 24 * 60 * 60 * 1000));
            booking2.setTotalAmount(new BigDecimal("325.00"));
            booking2.setStatus("PENDING");
            
            bookingRepository.save(booking2);
            
            log.info("Sample data initialized. Created {} sample bookings", bookingRepository.count());
        };
    }
}
