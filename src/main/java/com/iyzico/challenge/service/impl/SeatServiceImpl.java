package com.iyzico.challenge.service.impl;



import javax.transaction.Transactional;
import com.iyzico.challenge.dto.SeatDto;
import com.iyzico.challenge.dto.SeatInfoDto;
import com.iyzico.challenge.dto.SeatRequestDto;
import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.entity.Seat;
import com.iyzico.challenge.enums.Available;
import com.iyzico.challenge.enums.Reservation;
import com.iyzico.challenge.enums.State;
import com.iyzico.challenge.repository.FlightRepository;
import com.iyzico.challenge.repository.SeatRepository;
import com.iyzico.challenge.service.SeatService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Override
    public SeatDto save(SeatRequestDto seatRequestDto) {
        Seat seat = new Seat();
        seat.setSeatNumber(seatRequestDto.getSeatNumber());
        seat.setPrice(seatRequestDto.getPrice());
        seat.setState(State.ACTIVE);
        seat.setReservation(Reservation.NOT_RESERVED);

        Optional<Flight> flightOptional = flightRepository.findById(seatRequestDto.getFlightId());
        if (flightOptional.isPresent()) {
            seat.setFlight(flightOptional.get());
        } else {
            throw new RuntimeException("Flight not found");
        }

        Seat savedSeat = seatRepository.save(seat);
        return modelMapper.map(savedSeat, SeatDto.class);
    }

    @Override
    public SeatDto update(SeatRequestDto seatRequestDto) {
        SeatDto oldDto = this.getById(seatRequestDto.getId());

        if(seatRequestDto.getFlightId() != null){
            Optional<Flight> flightOptional = flightRepository.findById(seatRequestDto.getFlightId());
            if (flightOptional.isPresent()) {
                oldDto.setFlight(flightOptional.get());
            } else {
                throw new RuntimeException("Flight not found");
            }
        }

        oldDto.setSeatNumber(seatRequestDto.getSeatNumber());
        oldDto.setPrice(seatRequestDto.getPrice());
        oldDto.setSeatNumber(seatRequestDto.getSeatNumber());

        oldDto.setUpdated(new Date());

        return this.modelMapper.map(
                this.seatRepository.save(
                        this.modelMapper.map(
                                oldDto,
                                Seat.class
                        )
                ),
                SeatDto.class
        );
    }

    @Override
    public SeatDto getById(Long id) {
        return this.seatRepository.findById(id)
                .map(seat -> modelMapper.map(seat, SeatDto.class))
                .orElse(null);
    }

    @Override
    public List<SeatDto> getAll(State state, Available available) {
        return this.seatRepository.findByStateAndAvailable(state,available).stream()
                .map(seat -> this.modelMapper.map(seat, SeatDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SeatInfoDto> getAllByFlighId(Long flightId) {
        return this.seatRepository.findByFlightId(flightId).stream()
                .map(seat -> this.modelMapper.map(seat, SeatInfoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(SeatDto seatDto) {
        seatDto.setState(State.PASSIVE);
        seatDto.setUpdated(new Date());
        this.modelMapper.map(
                this.seatRepository.save(
                        this.modelMapper.map(
                                seatDto,
                                Seat.class
                        )
                ),
                SeatDto.class
        );
    }

    @Override
    public void reservation(SeatDto seatDto) {

    }

    @Override
    @Transactional
    public synchronized SeatDto updateSeatAvaible(SeatDto seatDto) {
        SeatDto oldDto = this.getById(seatDto.getId());

        oldDto.setAvailable(seatDto.getAvailable());

        oldDto.setUpdated(new Date());
        return this.modelMapper.map(
                this.seatRepository.save(
                        this.modelMapper.map(
                                oldDto,
                                Seat.class
                        )
                ),
                SeatDto.class
        );
    }
}