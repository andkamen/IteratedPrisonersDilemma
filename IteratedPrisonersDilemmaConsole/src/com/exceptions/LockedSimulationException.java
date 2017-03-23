package com.exceptions;

public class LockedSimulationException extends RuntimeException {

    private static final String LOCKED_SIMULATION =
            "'%s' simulation is locked. Addition and removal of strategies is not allowed.";

    public LockedSimulationException(String command) {
        super(String.format(LOCKED_SIMULATION, command));
    }
}
