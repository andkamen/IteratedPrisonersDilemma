package com.ipdweb.areas.user.servicesImpl;

import com.ipdweb.areas.user.entities.FacebookUser;
import com.ipdweb.areas.user.repositories.FacebookUserRepository;
import com.ipdweb.areas.user.services.FacebookUserService;
import com.ipdweb.areas.user.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Service;

@Service
public class FacebookUserServiceImpl implements FacebookUserService {

    @Autowired
    private FacebookUserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Override
    public void registerOrLogin(User fbUser) {
        String email = fbUser.getEmail();
        FacebookUser facebookUser = this.userRepository.findOneByUsername(email);
        if (facebookUser == null) {
            facebookUser = registerUser(email);
        }

        loginUser(facebookUser);
    }

    private FacebookUser registerUser(String email) {
        FacebookUser user = new FacebookUser();
        user.setUsername(email);
        user.setProvider("FACEBOOK");
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.addRole(this.roleService.getDefaultRole());
        this.userRepository.save(user);
        return user;
    }

    private void loginUser(FacebookUser facebookUser) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(facebookUser, null, facebookUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
