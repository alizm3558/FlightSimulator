package com.iyzico.challenge.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightInfoDto {
    private FlightDto flight;
    private List<SeatInfoDto> seats;
}
