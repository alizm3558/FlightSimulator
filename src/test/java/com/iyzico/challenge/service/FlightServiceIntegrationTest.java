package com.iyzico.challenge.service;

import com.iyzico.challenge.dto.FlightDto;
import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class FlightServiceIntegrationTest {
    @Autowired
    private FlightService flightService;

    @MockBean
    private FlightRepository flightRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    public void testGetFlightById() {
        Long flightId = 1L;
        Flight flight = new Flight();
        flight.setId(flightId);
        flight.setName("Test Flight");

        FlightDto flightDto = new FlightDto();
        flightDto.setName("Test Flight");

        when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));
        when(modelMapper.map(any(Flight.class), eq(FlightDto.class))).thenReturn(flightDto);

        FlightDto result = flightService.getById(flightId);

        assertEquals("Test Flight", result.getName());
        System.out.println("Test result: "+result.getName());
    }
}
