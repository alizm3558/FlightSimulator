package com.iyzico.challenge.repository;

import com.iyzico.challenge.entity.Seat;
import com.iyzico.challenge.enums.Available;
import com.iyzico.challenge.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByStateAndAvailable(State state, Available available);

    List<Seat> findByFlightId(Long flightId);


}
