package com.iyzico.challenge.controller;

import com.iyzico.challenge.dto.FlightDto;
import com.iyzico.challenge.dto.PaymentRequestDto;
import com.iyzico.challenge.dto.SeatDto;
import com.iyzico.challenge.entity.Payment;
import com.iyzico.challenge.enums.Available;
import com.iyzico.challenge.service.BankPaymentRequest;
import com.iyzico.challenge.service.PaymentService;
import com.iyzico.challenge.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private SeatService seatService;

    @PostMapping
    public ResponseEntity<?> salesPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        try {
            SeatDto seat = seatService.getById(paymentRequestDto.getSeatId());
            if (seat.getAvailable() == Available.UNAVAILABLE) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("The seat has been sold!");
            }
            Payment payment = paymentService.processPayment(paymentRequestDto.getPrice(), seat);
            return ResponseEntity.ok(payment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Another user is currently processing this seat!");
        }
    }
}
