package com.ipdweb.areas.user.servicesImpl;

import com.ipdweb.areas.common.utils.Constants;
import com.ipdweb.areas.user.entities.Role;
import com.ipdweb.areas.user.repositories.RoleRepository;
import com.ipdweb.areas.user.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByAuthority(String authority) {
        return this.roleRepository.getRoleByAuthority(authority);
    }

    @Override
    public Role getDefaultRole() {
        return this.roleRepository.getRoleByAuthority(Constants.DEFAULT_ROLE);
    }
}
