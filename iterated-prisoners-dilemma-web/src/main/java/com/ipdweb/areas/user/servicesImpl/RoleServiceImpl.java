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

    @Override
    public  Role getRoleByAuthority(String authority){
        return this.roleRepository.getRoleByAuthority(authority);
    }
}
