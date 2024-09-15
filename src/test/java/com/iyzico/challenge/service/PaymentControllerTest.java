package com.iyzico.challenge.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.iyzico.challenge.controller.PaymentController;
import com.iyzico.challenge.dto.PaymentRequestDto;
import com.iyzico.challenge.dto.SeatDto;
import com.iyzico.challenge.entity.Payment;
import com.iyzico.challenge.enums.Available;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SeatService seatService;

    @MockBean
    private PaymentService paymentService;

    @Test
    public void testSalesPaymentSuccess() throws Exception {
        PaymentRequestDto requestDto = new PaymentRequestDto();
        requestDto.setSeatId(1L);
        requestDto.setPrice(new BigDecimal("100.00"));

        SeatDto seat = new SeatDto();
        seat.setAvailable(Available.AVAILABLE);

        Payment payment = new Payment();
        payment.setId(1L);

        given(seatService.getById(anyLong())).willReturn(seat);
        given(paymentService.processPayment(any(BigDecimal.class), any(SeatDto.class))).willReturn(payment);

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk());

        System.out.println("-------testSalesPaymentSuccess end-----------");
    }

    @Test
    public void testSalesPaymentSeatUnavailable() throws Exception {
        PaymentRequestDto requestDto = new PaymentRequestDto();
        requestDto.setSeatId(1L);
        requestDto.setPrice(new BigDecimal("100.00"));

        SeatDto seat = new SeatDto();
        seat.setAvailable(Available.UNAVAILABLE);

        given(seatService.getById(anyLong())).willReturn(seat);

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string("The seat has been sold!"));

        System.out.println("-------testSalesPaymentSeatUnavailable end-----------");
    }

    @Test
    public void testSalesPaymentRuntimeException() throws Exception {
        PaymentRequestDto requestDto = new PaymentRequestDto();
        requestDto.setSeatId(1L);
        requestDto.setPrice(new BigDecimal("100.00"));

        SeatDto seat = new SeatDto();
        seat.setAvailable(Available.AVAILABLE);

        given(seatService.getById(anyLong())).willReturn(seat);
        willThrow(new RuntimeException("Another user is currently processing this seat!")).given(paymentService).processPayment(any(BigDecimal.class), any(SeatDto.class));

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string("Another user is currently processing this seat!"));


        System.out.println("-------testSalesPaymentRuntimeException end-----------");
    }
}