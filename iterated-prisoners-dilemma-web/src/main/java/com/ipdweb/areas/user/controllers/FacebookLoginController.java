package com.ipdweb.areas.user.controllers;

import com.ipdweb.areas.user.services.FacebookUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    //TODO logs in despite being disabled. How do you handle error message for a pop up with said info.
    @GetMapping("/facebook")
    public String registerOrLogin(){
        if(connectionRepository.findPrimaryConnection(Facebook.class) == null){
            return "redirect:/connect/facebook";
        }

        String userKey = this.connectionRepository.findPrimaryConnection(Facebook.class).getKey().getProviderUserId();
        String [] fields = {"id", "email"};
        User facebookUser = this.facebook.fetchObject(userKey, User.class, fields);
        this.facebookUserService.registerOrLogin(facebookUser);

        return "redirect:/";
    }
}
