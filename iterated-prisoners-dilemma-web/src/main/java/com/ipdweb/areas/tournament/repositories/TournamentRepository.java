package com.ipdweb.areas.tournament.repositories;

import com.ipdweb.areas.tournament.entities.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament,Long> {

    Tournament getTournamentById(Long id);

    @Query(value = "select t from Tournament as t order by t.id")
    Set<Tournament> getAllTournaments();

}
