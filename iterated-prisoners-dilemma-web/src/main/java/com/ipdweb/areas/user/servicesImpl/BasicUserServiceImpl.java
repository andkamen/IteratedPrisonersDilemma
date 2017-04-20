package com.ipdweb.areas.user.servicesImpl;

import com.ipdweb.areas.common.utils.Constants;
import com.ipdweb.areas.user.entities.BasicUser;
import com.ipdweb.areas.user.entities.Role;
import com.ipdweb.areas.user.entities.User;
import com.ipdweb.areas.user.errors.Errors;
import com.ipdweb.areas.user.exceptions.AccountDisabledException;
import com.ipdweb.areas.user.models.bindingModels.RegistrationModel;
import com.ipdweb.areas.user.models.viewModels.UserViewModel;
import com.ipdweb.areas.user.repositories.AllUserRepository;
import com.ipdweb.areas.user.services.BasicUserService;
import com.ipdweb.areas.user.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BasicUserServiceImpl implements BasicUserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AllUserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void register(RegistrationModel registrationModel) {
        BasicUser user = this.modelMapper.map(registrationModel, BasicUser.class);
        String encryptedPassword = this.bCryptPasswordEncoder.encode(registrationModel.getPassword());
        user.setPassword(encryptedPassword);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        user.addRole(this.roleService.getDefaultRole());

        this.userRepository.save(user);
    }

    @Override
    public List<UserViewModel> findAll() {
        List<UserViewModel> userViewModels = new ArrayList<>();
        List<User> users = this.userRepository.findAll();
        for (User user : users) {
            UserViewModel userViewModel = this.modelMapper.map(user, UserViewModel.class);
            for (Role role : user.getAuthorities()) {
                if (role.getAuthority().equals(Constants.ADMIN_ROLE)) {
                    userViewModel.setAdmin(true);
                }
            }
            userViewModels.add(userViewModel);
        }

        return userViewModels;
    }

    @Override
    public void changeAccountAccess(User user, boolean enabled) {
        if (user != null) {
            for (Role role : user.getAuthorities()) {
                if (role.getAuthority().equals(Constants.ADMIN_ROLE)) {
                    return;
                }
            }
            user.setEnabled(enabled);
            this.userRepository.save(user);
        }
    }

    @Override
    public User getUserById(Long id) {
        User user = this.userRepository.findById(id);

        return user;
    }

    @Override
    public void deleteUserById(Long id) {
        this.userRepository.delete(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findOneByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(Errors.INVALID_CREDENTIALS);
        }

        if (!user.isEnabled()) {
            throw new AccountDisabledException(Errors.ACCOUNT_DISABLED);
        }


        return user;
    }
}
