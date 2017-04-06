package com.ipdweb.areas.tournament.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "Can't access data you don't own")
public class UnauthorizedTournamentAccessException extends RuntimeException{
}
