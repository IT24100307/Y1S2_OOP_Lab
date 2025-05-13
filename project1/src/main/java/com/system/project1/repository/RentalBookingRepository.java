package com.system.project1.repository;

import com.system.project1.entity.RentalBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalBookingRepository extends JpaRepository<RentalBooking, Long> {
    List<RentalBooking> findByUserId(Long userId);

    RentalBooking findByBookingNumber(String bookingNumber);
}
