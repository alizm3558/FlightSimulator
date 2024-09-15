package com.iyzico.challenge.entity;

import com.iyzico.challenge.enums.State;
import com.iyzico.challenge.util.BaseObject;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Flight extends BaseObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @Column(name = "state")
    @Enumerated(EnumType.ORDINAL)
    private State state = State.ACTIVE;
}
