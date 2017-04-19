package com.ipdweb.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleGlobalExceptions() {

        return "error/error";
    }
//
//    @ExceptionHandler(AccountDisabledException.class)
//    public String catchDisabledException(Model model) {
//        model.addAttribute("error", Errors.ACCOUNT_DISABLED);
//
//        return "login";
//    }

}
