package com.iyzico.challenge.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.iyzico.challenge.dto.PaymentRequestDto;
import com.iyzico.challenge.dto.SeatDto;
import com.iyzico.challenge.entity.Payment;
import com.iyzico.challenge.enums.Available;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import java.math.BigDecimal;
import java.util.concurrent.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentControllerConcurrencyTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SeatService seatService;

    @MockBean
    private PaymentService paymentService;

    // Simulate two users trying to purchase the same seat at the same time
    @Test
    public void testConcurrentPaymentAccess() throws Exception {   PaymentRequestDto requestDto = new PaymentRequestDto();
        requestDto.setSeatId(1L);
        requestDto.setPrice(new BigDecimal("100.00"));

        SeatDto seat = new SeatDto();
        seat.setAvailable(Available.AVAILABLE);

        given(seatService.getById(anyLong())).willReturn(seat);
        willThrow(new RuntimeException("Another user is currently processing this seat!")).given(paymentService).processPayment(any(BigDecimal.class), any(SeatDto.class));

        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Callable<MvcResult> task = () -> {
            latch.await();
            return mockMvc.perform(post("/payments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(requestDto)))
                    .andReturn();
        };

        Future<MvcResult> future1 = executor.submit(task);
        Future<MvcResult> future2 = executor.submit(task);
        latch.countDown();

        MvcResult result1 = future1.get();
        MvcResult result2 = future2.get();

        System.out.println("Result 1: " + result1.getResponse().getContentAsString());
        System.out.println("Result 2: " + result2.getResponse().getContentAsString());

        // Expecting different results, since one should fail due to concurrent access
        assertNotEquals(result1.getResponse().getStatus(), result2.getResponse().getStatus(), "Both requests should not have the same result");

        executor.shutdown();
    }
}