package com.iyzico.challenge.enums;

public enum State {
    PASSIVE(0), ACTIVE(1);

    int order;

    State(int order){
        this.order=order;
    }
}

