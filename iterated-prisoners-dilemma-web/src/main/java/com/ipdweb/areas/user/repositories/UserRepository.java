package com.ipdweb.areas.user.repositories;

import com.ipdweb.areas.user.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;

@NoRepositoryBean
public interface UserRepository<T extends User> extends JpaRepository<T, Long> {

    T findOneByUsername(String username);

    T findById(Long id);

    @Query(value = "SELECT u FROM User AS u")
    List<T> findAll();

    @Query(value = "SELECT u FROM User AS u " +
            "WHERE u.username LIKE CONCAT('%', :searchWord, '%')")
    Page<T> findAllByUsername(@Param("searchWord") String searchWord, Pageable pageable);
}
