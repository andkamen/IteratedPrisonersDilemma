package com.ipdweb.areas.user.services;

import com.ipdweb.areas.user.entities.User;
import com.ipdweb.areas.user.models.bindingModels.RegistrationModel;
import com.ipdweb.areas.user.models.viewModels.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface BasicUserService extends UserDetailsService {

    void register(RegistrationModel registrationModel);

    List<UserViewModel> findAll();

    void changeAccountAccess(User user, boolean enabled);

    User getUserById(Long id);

    void deleteUserById(Long id);

    User getUserByUsername(String username);

    boolean isUsernameAvailable(String username);
}