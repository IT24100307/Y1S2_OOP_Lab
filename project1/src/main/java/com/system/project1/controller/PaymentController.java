package com.system.project1.controller;

import com.stripe.exception.StripeException;
import com.system.project1.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Create a payment intent for a booking
     */
    @PostMapping("/create-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestParam Long bookingId,
            @RequestParam String paymentMethodId) {
        try {
            Map<String, Object> response = paymentService.createPaymentIntent(bookingId, paymentMethodId);
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            log.error("Error creating payment intent", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Error processing payment request", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Confirm a payment after client-side confirmation
     */
    @PostMapping("/confirm")
    public ResponseEntity<?> confirmPayment(@RequestParam String paymentIntentId) {
        try {
            Map<String, Object> response = paymentService.confirmPayment(paymentIntentId);
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            log.error("Error confirming payment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Error processing confirmation request", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Stripe webhook endpoint for receiving events
     */
    @PostMapping("/webhook")
    public ResponseEntity<?> handleStripeWebhook(@RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {
        // In a production implementation, you would validate and process Stripe webhook
        // events here
        log.info("Received Stripe webhook");
        return ResponseEntity.ok(Map.of("status", "received"));
    }
}
