package com.system.project1.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentIntentCreateParams;
import com.system.project1.entity.PaymentDetails;
import com.system.project1.entity.RentalBooking;
import com.system.project1.repository.PaymentRepository;
import com.system.project1.repository.RentalBookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RentalBookingRepository rentalBookingRepository;

    /**
     * Create a payment intent with Stripe
     */
    public Map<String, Object> createPaymentIntent(Long bookingId, String paymentMethodId) throws StripeException {
        RentalBooking booking = rentalBookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with id: " + bookingId));

        // Amount in cents (Stripe requires amount in smallest currency unit)
        long amountInCents = booking.getTotalAmount().multiply(new BigDecimal("100")).longValue();

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setCurrency("usd")
                .setAmount(amountInCents)
                .setPaymentMethod(paymentMethodId)
                .setConfirmationMethod(PaymentIntentCreateParams.ConfirmationMethod.MANUAL)
                .setDescription("Vehicle Rental Payment - Booking #" + booking.getBookingNumber())
                .putMetadata("booking_id", booking.getId().toString())
                .build();

        PaymentIntent intent = PaymentIntent.create(params);

        Map<String, Object> response = new HashMap<>();
        response.put("clientSecret", intent.getClientSecret());
        response.put("paymentIntentId", intent.getId());
        response.put("requiresAction", intent.getStatus().equals("requires_action"));

        // Save initial payment record
        PaymentDetails payment = new PaymentDetails();
        payment.setPaymentIntentId(intent.getId());
        payment.setPaymentMethodId(paymentMethodId);
        payment.setAmount(booking.getTotalAmount());
        payment.setCurrency("USD");
        payment.setStatus(intent.getStatus());
        payment.setPaymentDate(new Date());
        payment.setRentalBooking(booking);

        paymentRepository.save(payment);

        return response;
    }

    /**
     * Confirm payment after client-side confirmation
     */
    @Transactional
    public Map<String, Object> confirmPayment(String paymentIntentId) throws StripeException {
        PaymentIntent intent = PaymentIntent.retrieve(paymentIntentId);

        if ("requires_confirmation".equals(intent.getStatus())) {
            intent = intent.confirm();
        }

        PaymentDetails payment = paymentRepository.findByPaymentIntentId(paymentIntentId);
        if (payment == null) {
            throw new IllegalStateException("Payment record not found for intent ID: " + paymentIntentId);
        }

        // Update payment record
        payment.setStatus(intent.getStatus());

        if ("succeeded".equals(intent.getStatus())) {
            payment.setReceiptUrl(intent.getCharges().getData().get(0).getReceiptUrl());

            // Get payment method details
            PaymentMethod paymentMethod = PaymentMethod.retrieve(payment.getPaymentMethodId());
            payment.setCardBrand(paymentMethod.getCard().getBrand());
            payment.setLast4(paymentMethod.getCard().getLast4());

            // Update booking status
            RentalBooking booking = payment.getRentalBooking();
            booking.setStatus("CONFIRMED");
            rentalBookingRepository.save(booking);
        }

        paymentRepository.save(payment);

        Map<String, Object> response = new HashMap<>();
        response.put("status", intent.getStatus());
        response.put("paymentIntentId", intent.getId());

        if ("succeeded".equals(intent.getStatus())) {
            response.put("success", true);
            response.put("receiptUrl", payment.getReceiptUrl());
        } else if ("requires_action".equals(intent.getStatus())) {
            response.put("requiresAction", true);
            response.put("clientSecret", intent.getClientSecret());
        } else {
            response.put("success", false);
            response.put("error", "Payment failed or requires additional steps");
        }

        return response;
    }
}
