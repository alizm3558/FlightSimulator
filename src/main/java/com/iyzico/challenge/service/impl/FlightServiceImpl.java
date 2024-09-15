package com.iyzico.challenge.service.impl;


import com.iyzico.challenge.dto.FlightDto;
import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.enums.State;
import com.iyzico.challenge.repository.FlightRepository;
import com.iyzico.challenge.service.FlightService;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private FlightRepository flightRepository;

    @Override
    @SneakyThrows
    public FlightDto save(FlightDto flightDto) {
        flightDto.setState(State.ACTIVE);
        return this.modelMapper.map(
                this.flightRepository.save(
                        this.modelMapper.map(flightDto, Flight.class)
                ), FlightDto.class
        );
    }

    @Override
    public FlightDto update(FlightDto flightDto) {
        FlightDto oldDto = this.getById(flightDto.getId());

        oldDto.setDescription(flightDto.getDescription());
        oldDto.setName(flightDto.getName());
        oldDto.setUpdated(new Date());
        return this.modelMapper.map(
                this.flightRepository.save(
                        this.modelMapper.map(
                                oldDto,
                                Flight.class
                        )
                ),
                FlightDto.class
        );
    }

    @Override
    public FlightDto getById(Long id) {
        return this.flightRepository.findById(id)
                .map(flight -> modelMapper.map(flight, FlightDto.class))
                .orElse(null);
    }

    @Override
    public List<FlightDto> getAll(State state) {
        return this.flightRepository.findByState(state).stream()
                .map(flight -> this.modelMapper.map(flight, FlightDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(FlightDto flightDto) {
        flightDto.setState(State.PASSIVE);
        flightDto.setUpdated(new Date());
        this.modelMapper.map(
                this.flightRepository.save(
                        this.modelMapper.map(
                                flightDto,
                                Flight.class
                        )
                ),
                FlightDto.class
        );
    }
}
