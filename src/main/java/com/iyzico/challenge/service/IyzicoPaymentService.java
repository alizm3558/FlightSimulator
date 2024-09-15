package com.iyzico.challenge.service;

import com.iyzico.challenge.entity.Payment;
import com.iyzico.challenge.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class IyzicoPaymentService {

    private Logger logger = LoggerFactory.getLogger(IyzicoPaymentService.class);

    private BankService bankService;
    private PaymentRepository paymentRepository;

    private AsyncPaymentService asyncPaymentService;

    public IyzicoPaymentService(BankService bankService, PaymentRepository paymentRepository, AsyncPaymentService asyncPaymentService) {
        this.bankService = bankService;
        this.paymentRepository = paymentRepository;
        this.asyncPaymentService=asyncPaymentService;
    }

    public void pay(BigDecimal price) {
        //pay with bank
        BankPaymentRequest request = new BankPaymentRequest();
        request.setPrice(price);
        BankPaymentResponse response = bankService.pay(request);

        //insert records
        Payment payment = new Payment();
        payment.setBankResponse(response.getResultCode());
        payment.setPrice(price);
        asyncPaymentService.savePaymentAsync(payment);// asekron olarak kaydetmektedir
        logger.info("Payment saved successfully!");
    }
}
