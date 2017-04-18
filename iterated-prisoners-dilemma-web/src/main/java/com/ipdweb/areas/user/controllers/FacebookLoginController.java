package com.ipdweb.areas.user.controllers;

import com.ipdweb.areas.user.errors.Errors;
import com.ipdweb.areas.user.exceptions.AccountDisabledException;
import com.ipdweb.areas.user.services.FacebookUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/register")
public class FacebookLoginController {

    @Autowired
    private FacebookUserService facebookUserService;

    private Facebook facebook;

    private ConnectionRepository connectionRepository;

    public FacebookLoginController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @GetMapping("/facebook")
    public String registerOrLogin() {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }
        ConnectionKey connectionKey = this.connectionRepository.findPrimaryConnection(Facebook.class).getKey();

        //[START]
        //Hack due to different API versions
        String userKey = this.connectionRepository.findPrimaryConnection(Facebook.class).getKey().getProviderUserId();
        String[] fields = {"id", "email"};
        User facebookUser = this.facebook.fetchObject(userKey, User.class, fields);
        this.facebookUserService.registerOrLogin(facebookUser);
        //[END]

        this.connectionRepository.removeConnection(connectionKey);

        return "redirect:/";
    }

    @ExceptionHandler(AccountDisabledException.class)
    public String catchDisabledException(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", Errors.ACCOUNT_DISABLED);

        return "redirect:/login";
    }
}
