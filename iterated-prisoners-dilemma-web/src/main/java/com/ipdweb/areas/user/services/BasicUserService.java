package com.ipdweb.areas.user.services;

import com.ipdweb.areas.user.entities.User;
import com.ipdweb.areas.user.models.bindingModels.RegistrationModel;
import com.ipdweb.areas.user.models.viewModels.UserViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface BasicUserService extends UserDetailsService {

    void register(RegistrationModel registrationModel);

    Page<UserViewModel> findAll(Pageable pageable);

    Page<UserViewModel> searchUsersByUsername( String searchWord,Pageable pageable);

    void changeAccountAccess(User user, boolean enabled);

    User getUserById(Long id);

    void deleteUserById(Long id);

    User getUserByUsername(String username);

    boolean isUsernameAvailable(String username);
}