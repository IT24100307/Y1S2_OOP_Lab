package com.system.project1.repository;

import com.system.project1.entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentDetails, Long> {
    List<PaymentDetails> findByRentalBookingId(Long rentalBookingId);

    PaymentDetails findByPaymentIntentId(String paymentIntentId);
}
