package com.iyzico.challenge.enums;


public enum Reservation {
    NOT_RESERVED(0),  RESERVED(1), ;

    int order;

    Reservation(int order){
        this.order=order;
    }
}