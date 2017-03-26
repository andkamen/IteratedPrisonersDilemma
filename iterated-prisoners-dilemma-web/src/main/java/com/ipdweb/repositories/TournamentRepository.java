package com.ipdweb.repositories;

import com.ipdweb.entities.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament,Long> {

    Tournament getTournamentById(Long id);

}
