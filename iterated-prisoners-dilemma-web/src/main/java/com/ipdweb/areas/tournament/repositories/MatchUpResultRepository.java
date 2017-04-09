package com.ipdweb.areas.tournament.repositories;

import com.ipdweb.areas.tournament.entities.Tournament;
import com.ipdweb.areas.tournament.entities.TournamentMatchUpResult;
import com.ipdweb.areas.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MatchUpResultRepository extends JpaRepository<TournamentMatchUpResult, Long> {


    @Query(value = "select t from Tournament as t where t.user = :user order by t.id ")
    Set<Tournament> getAllTournaments(@Param("user") User user);


}
