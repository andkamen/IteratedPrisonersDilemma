package com.ipdweb.areas.user.servicesImpl;

import com.ipdweb.areas.user.entities.BasicUser;
import com.ipdweb.areas.user.entities.Role;
import com.ipdweb.areas.user.entities.User;
import com.ipdweb.areas.user.errors.Errors;
import com.ipdweb.areas.user.models.bindingModels.RegistrationModel;
import com.ipdweb.areas.user.models.viewModels.UserViewModel;
import com.ipdweb.areas.user.repositories.BasicUserRepository;
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
    private BasicUserRepository userRepository;

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
        List<BasicUser> users = this.userRepository.findAll();
        for (User user : users) {
            UserViewModel userViewModel = this.modelMapper.map(user, UserViewModel.class);
            for (Role role : user.getAuthorities()) {
                if (role.getAuthority().equals("ROLE_ADMIN")) {
                    userViewModel.setAdmin(true);
                }
            }
            userViewModels.add(userViewModel);
        }

        return userViewModels;
    }

    @Override
    public void disableUser(BasicUser user) {
        if (user != null) {
            for (Role role : user.getAuthorities()) {
                if (role.getAuthority().equals("ROLE_ADMIN")) {
                    return;
                }
            }
            user.setEnabled(false);
            this.userRepository.save(user);
        }
    }

    @Override
    public void enableUser(BasicUser user) {
        if (user != null) {
            for (Role role : user.getAuthorities()) {
                if (role.getAuthority().equals("ROLE_ADMIN")) {
                    return;
                }
            }
            user.setEnabled(true);
            this.userRepository.save(user);
        }
    }

    @Override
    public BasicUser getUserById(Long id) {
        BasicUser user = this.userRepository.findById(id);

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

        return user;
    }
}
