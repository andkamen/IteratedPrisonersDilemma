package com.ipdweb.areas.user.servicesImpl;

import com.ipdweb.areas.common.utils.Constants;
import com.ipdweb.areas.user.entities.FacebookUser;
import com.ipdweb.areas.user.entities.Role;
import com.ipdweb.areas.user.errors.Errors;
import com.ipdweb.areas.user.exceptions.AccountDisabledException;
import com.ipdweb.areas.user.repositories.AllUserRepository;
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
    private AllUserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Override
    public void registerOrLogin(User fbUser) {
        String email = fbUser.getEmail();
        com.ipdweb.areas.user.entities.User facebookUser = this.userRepository.findOneByUsername(email);
        if (facebookUser == null) {
            facebookUser = registerUser(email);
        }

        loginUser(facebookUser);
    }



    @Override
    public com.ipdweb.areas.user.entities.User getUserById(Long id) {
        com.ipdweb.areas.user.entities.User user = this.userRepository.findById(id);

        return user;
    }

    private FacebookUser registerUser(String email) {
        FacebookUser user = new FacebookUser();
        user.setUsername(email);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.addRole(this.roleService.getDefaultRole());
        this.userRepository.save(user);
        return user;
    }

    private void loginUser(com.ipdweb.areas.user.entities.User facebookUser) {
        if (!facebookUser.isEnabled()) {
            throw new AccountDisabledException(Errors.ACCOUNT_DISABLED);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(facebookUser, null, facebookUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}
