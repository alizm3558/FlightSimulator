package com.iyzico.challenge.service.impl;

import com.iyzico.challenge.dto.SeatDto;
import com.iyzico.challenge.entity.Payment;
import com.iyzico.challenge.enums.Available;
import com.iyzico.challenge.repository.PaymentRepository;
import com.iyzico.challenge.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private BankService bankService;
    @Autowired
    private SeatService seatService;

    @Override
    @Transactional
    public synchronized Payment processPayment(BigDecimal price, SeatDto seat) {
    try {


        BankPaymentRequest bankPaymentRequest = new BankPaymentRequest();
        seat.setAvailable(Available.UNAVAILABLE);
        seat.setVersion(seat.getVersion());
        seatService.updateSeatAvaible(seat);
        Payment payment = new Payment();
        payment.setPrice(price);

        bankPaymentRequest.setPrice(price);
        BankPaymentResponse bankPaymentResponse = bankService.pay(bankPaymentRequest);

        payment.setBankResponse(bankPaymentResponse.getResultCode());
        payment.setSeatId(seat.getId());

        return paymentRepository.save(payment);
    } catch (OptimisticLockingFailureException e){
        throw new RuntimeException("The seat has been sold during the transaction!");

    }

    }
}