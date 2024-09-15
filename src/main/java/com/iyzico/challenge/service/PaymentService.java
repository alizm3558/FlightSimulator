package com.iyzico.challenge.service;

import com.iyzico.challenge.dto.SeatDto;
import com.iyzico.challenge.entity.Payment;

import java.math.BigDecimal;

public interface PaymentService {

    Payment processPayment(BigDecimal price, SeatDto seat);
}
