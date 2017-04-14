package com.ipdweb.areas.user.services;

import org.springframework.social.facebook.api.User;

public interface FacebookUserService {

    void registerOrLogin(User facebookUser);
}
