package com.ipdweb.areas.user.servicesImpl;

import com.ipdweb.areas.user.entities.User;
import com.ipdweb.areas.user.errors.Errors;
import com.ipdweb.areas.user.models.bindingModels.RegistrationModel;
import com.ipdweb.areas.user.models.viewModels.UserViewModel;
import com.ipdweb.areas.user.repositories.UserRepository;
import com.ipdweb.areas.user.services.RoleService;
import com.ipdweb.areas.user.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void register(RegistrationModel registrationModel) {
        User user = this.modelMapper.map(registrationModel, User.class);
        String encryptedPassword = this.bCryptPasswordEncoder.encode(registrationModel.getPassword());
        user.setPassword(encryptedPassword);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        user.getAuthorities().add(this.roleService.getRoleByAuthority("ROLE_USER"));

        this.userRepository.save(user);
    }

    @Override
    public List<UserViewModel> findAll() {
        List<UserViewModel> userViewModels = new ArrayList<>();
        List<User> users = this.userRepository.findAll();
        for (User user : users) {
            UserViewModel userViewModel = this.modelMapper.map(user, UserViewModel.class);
            userViewModels.add(userViewModel);
        }

        return userViewModels;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findOneByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(Errors.INVALID_CREDENTIALS);
        }

        return user;
    }
}
