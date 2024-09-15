package com.iyzico.challenge.dto;

import com.iyzico.challenge.enums.Available;
import com.iyzico.challenge.enums.Reservation;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatInfoDto {

    private Long id;
    private String seatNumber;
    private Available available;
    private BigDecimal price;
    private Reservation reservation;
}
