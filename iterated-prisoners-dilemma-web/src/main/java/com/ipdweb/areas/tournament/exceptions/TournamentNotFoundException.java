package com.ipdweb.areas.tournament.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "No such tournament")
public class TournamentNotFoundException extends RuntimeException {
}
