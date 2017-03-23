package com.exceptions;

public class InvalidStrategyNameException extends RuntimeException {

    private static final String INVALID_STRATEGY_NAME =
            "'%s' is not a valid strategy name";

    public InvalidStrategyNameException(String command) {
        super(String.format(INVALID_STRATEGY_NAME, command));
    }
}
