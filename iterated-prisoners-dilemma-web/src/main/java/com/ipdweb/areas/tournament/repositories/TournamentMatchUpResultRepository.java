package com.ipdweb.areas.tournament.repositories;

import com.ipdweb.areas.tournament.entities.TournamentMatchUpResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentMatchUpResultRepository extends JpaRepository<TournamentMatchUpResult,Long> {
}
