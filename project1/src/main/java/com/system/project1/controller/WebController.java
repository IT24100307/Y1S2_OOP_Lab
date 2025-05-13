package com.system.project1.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @Value("${stripe.public.key}")
    private String stripePublicKey;

    /**
     * Redirect from root to payment demo
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/sample-frontend/payment-simplified.html";
    }

    /**
     * Demo page for Stripe integration (if using Thymeleaf)
     * This is just for demo purposes - can be removed if not using Thymeleaf
     */
    @GetMapping("/payment-demo")
    public String paymentDemo(Model model) {
        model.addAttribute("stripePublicKey", stripePublicKey);
        return "payment-demo";
    }

    /**
     * Direct access to the payment page
     */
    @GetMapping("/payment")
    public String paymentPage() {
        return "redirect:/sample-frontend/payment-simplified.html";
    }
}
