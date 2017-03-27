package com.ipdweb.repositories;

import com.ipdweb.entities.TournamentMatchUpResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentMatchUpResultRepository extends JpaRepository<TournamentMatchUpResult,Long> {
}
