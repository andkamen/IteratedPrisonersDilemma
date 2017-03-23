package com.exceptions;

public class KeyNotFoundException extends RuntimeException {

    private static final String NOT_REGISTERED_IN_DATA =
            "Simulation Type with that name is not registered in the date.";

    public KeyNotFoundException() {
        super(NOT_REGISTERED_IN_DATA);
    }
}