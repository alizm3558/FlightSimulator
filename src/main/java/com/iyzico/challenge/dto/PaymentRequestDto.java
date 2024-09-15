package com.iyzico.challenge.dto;

import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {

    private Long seatId;
    private BigDecimal price;
}
