package com.ipdweb.areas.user.services;

import com.ipdweb.areas.user.entities.Role;

public interface RoleService {

    Role getRoleByAuthority(String authority);

    Role getDefaultRole();
}