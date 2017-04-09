package com.ipdweb.areas.simulation.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such Simulation")
public class SimulationNotFoundException extends RuntimeException {
}
