package com.ipdweb.areas.user.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Account has been disabled")
public class AccountDisabledException extends RuntimeException {
    public AccountDisabledException(String msg) {
        super(msg);
    }

    public AccountDisabledException(String msg, Throwable t) {
        super(msg, t);
    }
}
