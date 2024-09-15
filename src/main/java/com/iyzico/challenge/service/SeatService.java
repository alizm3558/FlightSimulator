package com.iyzico.challenge.service;

import com.iyzico.challenge.dto.FlightDto;
import com.iyzico.challenge.dto.SeatDto;
import com.iyzico.challenge.dto.SeatInfoDto;
import com.iyzico.challenge.dto.SeatRequestDto;
import com.iyzico.challenge.enums.Available;
import com.iyzico.challenge.enums.State;

import java.util.List;

public interface SeatService {
    SeatDto save(SeatRequestDto seatRequestDto);

    SeatDto update(SeatRequestDto seatRequestDto);

    SeatDto getById(Long id);

    List<SeatDto> getAll(State state, Available available);

    List<SeatInfoDto> getAllByFlighId(Long flightId);

    void delete(SeatDto seatDto);

    void reservation(SeatDto seatDto);

    SeatDto updateSeatAvaible(SeatDto seatDto);
}
