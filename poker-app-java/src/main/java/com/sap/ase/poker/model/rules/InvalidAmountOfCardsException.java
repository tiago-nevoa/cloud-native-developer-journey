package com.sap.ase.poker.model.rules;

public class InvalidAmountOfCardsException extends RuntimeException {
    private static final long serialVersionUID = 4853174149135885572L;

    public InvalidAmountOfCardsException(String message) {
        super(message);
    }
}
