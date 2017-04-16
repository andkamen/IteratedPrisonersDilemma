package com.ipdweb.areas.user.services;

import com.ipdweb.areas.user.entities.FacebookUser;
import org.springframework.social.facebook.api.User;

public interface FacebookUserService {

    void registerOrLogin(User facebookUser);

    void disableUser(FacebookUser user);

    void enableUser(FacebookUser user);

    FacebookUser getUserById(Long id);

    void deleteUserById(Long id);
}
