package com.iyzico.challenge.controller;


import com.iyzico.challenge.dto.SeatDto;
import com.iyzico.challenge.dto.SeatRequestDto;
import com.iyzico.challenge.enums.Available;
import com.iyzico.challenge.enums.State;
import com.iyzico.challenge.service.SeatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seat")
public class SeatController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SeatService seatService;


    @GetMapping
    public ResponseEntity<List<SeatDto>> getAll() {
        return ResponseEntity.ok(seatService.getAll(State.ACTIVE, Available.AVAILABLE));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody SeatRequestDto seatRequestDto) {

        try {
            SeatDto seat = seatService.save(seatRequestDto);
            return ResponseEntity.ok(seat);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred on the server, please contact the relevant department.");
        }

    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody SeatRequestDto seatRequestDto) {
        SeatDto seat= seatService.getById(seatRequestDto.getId());
        if (seat == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Seat not found!");
        }
        return ResponseEntity.ok(seatService.update(seatRequestDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        SeatDto seatDto = seatService.getById(id);
        if (seatDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Seat not found!");
        }
        seatService.delete(seatDto);
        return ResponseEntity.status(HttpStatus.OK).body("Seat deleted.");
    }
}