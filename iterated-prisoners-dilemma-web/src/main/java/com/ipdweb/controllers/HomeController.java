package com.ipdweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHomePage() {

        return "home/home";
    }

    //TODO not entered when generic /error is called?
    @GetMapping("/error")
    public String getErrorPage() {
        return "error/error";
    }
}
