package com.iyzico.challenge.service;

import com.iyzico.challenge.dto.FlightDto;
import com.iyzico.challenge.enums.State;

import java.util.List;

public interface FlightService {
    FlightDto save(FlightDto flightDto);

    FlightDto update(FlightDto flightDto);

    FlightDto getById(Long id);

    List<FlightDto> getAll(State state);

    void delete(FlightDto flightDto);
}
