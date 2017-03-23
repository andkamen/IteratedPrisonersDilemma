package com.exceptions;

public class MissingCommandArgumentsException extends RuntimeException {

    private static final String MISSING_COMMAND_ARGUMENTS =
            "Arguments are missing from the last command.";

    public MissingCommandArgumentsException() {
        super(MISSING_COMMAND_ARGUMENTS);
    }

}
