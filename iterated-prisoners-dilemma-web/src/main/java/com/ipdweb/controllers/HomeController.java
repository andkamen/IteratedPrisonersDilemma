package com.ipdweb.controllers;

import com.ipdweb.areas.user.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHomePage(Authentication authentication, Model model) {
        if (authentication != null) {
            User loggedUser = (User) authentication.getPrincipal();
            model.addAttribute("loggedUserId", loggedUser.getId());
        }
        return "home";
    }

    @GetMapping("/error")
    public String getErrorPage(Authentication authentication, Model model) {
        if (authentication != null) {
            User loggedUser = (User) authentication.getPrincipal();
            model.addAttribute("loggedUserId", loggedUser.getId());
        }

        return "error";
    }
}
