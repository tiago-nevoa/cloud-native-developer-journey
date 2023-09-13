package com.sap.ase.poker.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * This class is internally used to identify illegal operations. Example:
 * checking when there are placed bets. This is an illegal
 * usage from the client, not a server error.
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalActionException extends RuntimeException {
    private static final long serialVersionUID = 5095496162899845935L;

    public IllegalActionException(String message) {
        super(message);
    }
}