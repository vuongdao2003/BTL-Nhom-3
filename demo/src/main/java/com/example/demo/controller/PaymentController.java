package com.example.demo.controller;

import com.example.demo.entity.Payment;
import com.example.demo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process-payment")
    public ResponseEntity<String> processPayment(@RequestParam int orderID) {
        String message = paymentService.processPayment(orderID);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/history")
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/payments/user/{userID}")
    public ResponseEntity<List<Payment>> getPaymentsByUser(@PathVariable int userID) {
        List<Payment> payments = paymentService.getPaymentsByUserID(userID);
        return ResponseEntity.ok(payments);
    }

}
