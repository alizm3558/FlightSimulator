package com.iyzico.challenge.repository;

import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findByState(State state);

    /* Optional<Flight> findById(Long id);*/
}
