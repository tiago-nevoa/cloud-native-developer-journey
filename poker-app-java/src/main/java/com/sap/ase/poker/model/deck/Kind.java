package com.sap.ase.poker.model.deck;

public enum Kind {
    TWO(1, "2"),
    THREE(2, "3"),
    FOUR(3, "4"),
    FIVE(4, "5"),
    SIX(5, "6"),
    SEVEN(6, "7"),
    EIGHT(7, "8"),
    NINE(8, "9"),
    TEN(9, "10"),
    JACK(10, "Jack"),
    QUEEN(11, "Queen"),
    KING(12, "King"),
    ACE(13, "Ace");

    public final int rank;
    public final String value;

    Kind(int rank, String value) {
        this.rank = rank;
        this.value = value;
    }

    public int getRank() {
        return rank;
    }
}
