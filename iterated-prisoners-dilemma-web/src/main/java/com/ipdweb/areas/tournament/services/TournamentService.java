package com.ipdweb.areas.tournament.services;

import com.ipdweb.areas.tournament.models.bindingModels.CreateTournamentBindingModel;
import com.ipdweb.areas.tournament.models.viewModels.TournamentPreviewViewModel;
import com.ipdweb.areas.tournament.models.viewModels.TournamentResultViewModel;

import java.util.Set;

public interface TournamentService {

    void save(CreateTournamentBindingModel createTournamentBindingModel);

    void resetTournament(Long id);

    void deleteTournamentById(Long id);

    TournamentResultViewModel getTournamentById(Long id);

    Set<TournamentPreviewViewModel> getAllTournaments();

   // tournament getTournamentById(Long id);
}
