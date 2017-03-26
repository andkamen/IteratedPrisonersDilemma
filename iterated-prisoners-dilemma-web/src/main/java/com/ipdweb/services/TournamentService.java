package com.ipdweb.services;

import com.ipdweb.entities.Tournament;

public interface TournamentService {

    void save(Tournament tournament);

    void resetTournament(Long id);

    void deleteTournamentById(Long id);

   // Tournament getTournamentById(Long id);
}
