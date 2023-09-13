package com.sap.ase.poker.model;

public class InvalidHandException extends RuntimeException {
    private static final long serialVersionUID = -4976446620871380102L;

    public InvalidHandException(String message) {
        super(message);
    }
}
