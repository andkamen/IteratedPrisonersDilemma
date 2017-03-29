package com.ipdweb.areas.user.repositories;

import com.ipdweb.areas.user.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{

    Role getRoleByAuthority(String authority);
}
