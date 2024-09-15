package com.iyzico.challenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iyzico.challenge.enums.State;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightDto {
    private Long id;
    private String name;
    private String description;

    @JsonIgnore
    private State state;

    @JsonIgnore
    private Date created;

    @JsonIgnore
    private Date updated;
}
