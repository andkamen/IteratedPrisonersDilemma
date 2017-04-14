package com.ipdweb.areas.user.servicesImpl;

import com.ipdweb.areas.user.entities.Role;
import com.ipdweb.areas.user.repositories.RoleRepository;
import com.ipdweb.areas.user.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public static final String DEFAULT_ROLE = "ROLE_USER";

    @Override
    public  Role getRoleByAuthority(String authority){
        return this.roleRepository.getRoleByAuthority(authority);
    }

    @Override
    public Role getDefaultRole() {
        return this.roleRepository.getRoleByAuthority(DEFAULT_ROLE);
    }
}
