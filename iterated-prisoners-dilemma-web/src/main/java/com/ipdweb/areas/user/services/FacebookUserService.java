package com.ipdweb.areas.user.services;

import com.ipdweb.areas.user.entities.FacebookUser;
import org.springframework.social.facebook.api.User;

public interface FacebookUserService {

    void registerOrLogin(User facebookUser);

    com.ipdweb.areas.user.entities.User getUserById(Long id);

}
