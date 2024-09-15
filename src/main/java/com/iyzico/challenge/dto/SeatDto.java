package com.iyzico.challenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.enums.Available;
import com.iyzico.challenge.enums.Reservation;
import com.iyzico.challenge.enums.State;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatDto {

    private Long id;
    private String seatNumber;
    private Available available;
    private BigDecimal price;
    private Flight flight;

    @JsonIgnore
    private Date created;

    @JsonIgnore
    private Date updated;

    @JsonIgnore
    private State state;

    @JsonIgnore
    private Reservation reservation;

    private Integer version; // Versiyon alanı eklendi
}
