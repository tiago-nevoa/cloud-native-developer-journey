package com.sap.ase.poker.model;

public enum GameState {

    OPEN(0),
    PRE_FLOP(1),
    FLOP(2),
    TURN(3),
    RIVER(4),
    ENDED(5);

    private final int value;

    GameState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
