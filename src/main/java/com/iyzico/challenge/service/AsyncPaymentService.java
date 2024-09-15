package com.iyzico.challenge.service;

import com.iyzico.challenge.entity.Payment;
import com.iyzico.challenge.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncPaymentService {

    private PaymentRepository paymentRepository;
    private Logger logger = LoggerFactory.getLogger(AsyncPaymentService.class);

    public AsyncPaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Async
    public CompletableFuture<Void> savePaymentAsync(Payment payment) {
        paymentRepository.save(payment);
        logger.info("Payment saved successfully in async process!");
        return CompletableFuture.completedFuture(null);
    }
}
