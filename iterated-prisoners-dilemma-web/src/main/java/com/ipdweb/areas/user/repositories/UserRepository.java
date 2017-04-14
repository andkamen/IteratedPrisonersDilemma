package com.ipdweb.areas.user.repositories;

import com.ipdweb.areas.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface UserRepository<T extends User> extends JpaRepository<T, Long> {

    T findOneByUsername(String username);

    @Query(value = "SELECT u FROM User AS u")
    List<T> findAll();
}
