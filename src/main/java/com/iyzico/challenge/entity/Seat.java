package com.iyzico.challenge.entity;

import com.iyzico.challenge.enums.Available;
import com.iyzico.challenge.enums.Reservation;
import com.iyzico.challenge.enums.State;
import com.iyzico.challenge.util.BaseObject;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class Seat extends BaseObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String seatNumber;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @Column(name="available")
    @Enumerated(EnumType.ORDINAL)
    private Available available=Available.AVAILABLE;

    @Column(name="state")
    @Enumerated(EnumType.ORDINAL)
    private State state=State.ACTIVE;

    @Column(name="reservation")
    @Enumerated(EnumType.ORDINAL)
    private Reservation reservation=Reservation.NOT_RESERVED;

    @Version
    private Integer version; //Optimistic Locking kullanımı için
}