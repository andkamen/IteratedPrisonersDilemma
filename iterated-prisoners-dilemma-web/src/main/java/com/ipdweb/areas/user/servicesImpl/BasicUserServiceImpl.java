package com.ipdweb.areas.user.servicesImpl;

import com.ipdweb.areas.common.utils.Constants;
import com.ipdweb.areas.user.entities.BasicUser;
import com.ipdweb.areas.user.entities.Role;
import com.ipdweb.areas.user.entities.User;
import com.ipdweb.areas.user.errors.Errors;
import com.ipdweb.areas.user.exceptions.AccountDisabledException;
import com.ipdweb.areas.user.exceptions.UserNotFoundException;
import com.ipdweb.areas.user.models.bindingModels.RegistrationModel;
import com.ipdweb.areas.user.models.viewModels.UserViewModel;
import com.ipdweb.areas.user.repositories.AllUserRepository;
import com.ipdweb.areas.user.services.BasicUserService;
import com.ipdweb.areas.user.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BasicUserServiceImpl implements BasicUserService {

    private AllUserRepository userRepository;
    private RoleService roleService;
    private ModelMapper modelMapper;

    @Autowired
    public BasicUserServiceImpl(AllUserRepository userRepository, RoleService roleService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void register(RegistrationModel registrationModel) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        BasicUser user = this.modelMapper.map(registrationModel, BasicUser.class);
        String encryptedPassword = bCryptPasswordEncoder.encode(registrationModel.getPassword());
        user.setPassword(encryptedPassword);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        user.addRole(this.roleService.getDefaultRole());

        this.userRepository.save(user);
    }

    @Override
    public Page<UserViewModel> findAll(Pageable pageable) {
        Page<User> users = this.userRepository.findAll(pageable);
        List<UserViewModel> userViewModels = new ArrayList<>();
        for (User user : users) {
            UserViewModel userViewModel = this.modelMapper.map(user, UserViewModel.class);
            for (Role role : user.getAuthorities()) {
                if (role.getAuthority().equals(Constants.ADMIN_ROLE)) {
                    userViewModel.setAdmin(true);
                    break;
                }
            }
            userViewModels.add(userViewModel);
        }

        Page<UserViewModel> userModels = new PageImpl<UserViewModel>(userViewModels, pageable, users.getTotalElements());
        return userModels;
    }

    @Override
    public Page<UserViewModel> searchUsersByUsername( String searchWord,Pageable pageable) {
        Page<User> users = this.userRepository.findAllByUsername(searchWord, pageable);
        List<UserViewModel> userViewModels = new ArrayList<>();
        for (User user : users) {
            UserViewModel userViewModel = this.modelMapper.map(user, UserViewModel.class);
            for (Role role : user.getAuthorities()) {
                if (role.getAuthority().equals(Constants.ADMIN_ROLE)) {
                    userViewModel.setAdmin(true);
                    break;
                }
            }
            userViewModels.add(userViewModel);
        }

        Page<UserViewModel> userModels = new PageImpl<UserViewModel>(userViewModels, pageable, users.getTotalElements());
        return userModels;
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

        if (user == null) {
            throw new UserNotFoundException();
        }

        return user;
    }

    @Override
    public void deleteUserById(Long id) {
        this.userRepository.delete(id);
    }

    @Override
    public User getUserByUsername(String username) {
        User user = this.userRepository.findOneByUsername(username);

        return user;
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        User user = this.userRepository.findOneByUsername(username);

        return user == null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
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
