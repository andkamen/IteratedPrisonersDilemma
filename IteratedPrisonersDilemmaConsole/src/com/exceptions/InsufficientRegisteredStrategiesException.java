package com.exceptions;

public class InsufficientRegisteredStrategiesException extends RuntimeException {

    private static final String INSUFFICIENT_REGISTERED_STRATEGIES =
            "At least 2 strategies must be registered.";

    public InsufficientRegisteredStrategiesException() {
        super(INSUFFICIENT_REGISTERED_STRATEGIES);
    }
}
