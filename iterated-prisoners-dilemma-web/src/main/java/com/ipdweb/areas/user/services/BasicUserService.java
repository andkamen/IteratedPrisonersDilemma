package com.ipdweb.areas.user.services;

import com.ipdweb.areas.user.entities.BasicUser;
import com.ipdweb.areas.user.models.bindingModels.RegistrationModel;
import com.ipdweb.areas.user.models.viewModels.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface BasicUserService extends UserDetailsService {

    void register(RegistrationModel registrationModel);

    List<UserViewModel> findAll();

    void disableUser(BasicUser user);

    void enableUser(BasicUser user);

    BasicUser getUserById(Long id);

    void deleteUserById(Long id);
}