package com.ipdweb.areas.simulation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Can't access data you don't own")
public class UnauthorizedSimulationAccessException extends RuntimeException {
}
