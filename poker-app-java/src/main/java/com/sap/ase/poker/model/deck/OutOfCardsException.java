package com.sap.ase.poker.model.deck;

public class OutOfCardsException extends RuntimeException {
    private static final long serialVersionUID = -3445884916921731847L;

    public OutOfCardsException(String message) {
        super(message);
    }
}
