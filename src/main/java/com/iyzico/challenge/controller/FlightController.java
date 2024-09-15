package com.iyzico.challenge.controller;


import com.iyzico.challenge.dto.FlightDto;
import com.iyzico.challenge.dto.FlightInfoDto;
import com.iyzico.challenge.dto.SeatInfoDto;
import com.iyzico.challenge.enums.State;
import com.iyzico.challenge.service.FlightService;
import com.iyzico.challenge.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flight")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private SeatService seatService;


    @GetMapping
    public ResponseEntity<List<FlightDto>> getAll() {
        return ResponseEntity.ok(flightService.getAll(State.ACTIVE));
    }


    @PostMapping
    public ResponseEntity<?> save(@RequestBody FlightDto flightDto) {

        return ResponseEntity.ok(flightService.save(flightDto));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody FlightDto flightDto) {
        FlightDto flight= flightService.getById(flightDto.getId());
        if (flight == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flight not found!");
        }
        return ResponseEntity.ok(flightService.update(flightDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        FlightDto flightDto = flightService.getById(id);
        if (flightDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flight not found!");
        }
        flightService.delete(flightDto);
        return ResponseEntity.status(HttpStatus.OK).body("Flight deleted.");
    }

    @GetMapping("/info/{flightId}")
    public ResponseEntity<?> getFlightAndSeats(@PathVariable Long flightId) {
        try {
            FlightDto flightDto = flightService.getById(flightId);
            List<SeatInfoDto> seatDtoList = seatService.getAllByFlighId(flightId);

            if (flightDto == null) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flight not found!");
            }

            FlightInfoDto flightInfoDto = new FlightInfoDto();
            flightInfoDto.setFlight(flightDto);
            flightInfoDto.setSeats(seatDtoList);

            return ResponseEntity.ok(flightInfoDto);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }
}
