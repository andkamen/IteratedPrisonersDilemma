package com.ipdweb.areas.tournament.services;

import com.ipdweb.areas.tournament.models.bindingModels.CreateTournamentBindingModel;
import com.ipdweb.areas.tournament.models.bindingModels.EditTournamentBindingModel;
import com.ipdweb.areas.tournament.models.bindingModels.SelectMatchUpResultsBindingModel;
import com.ipdweb.areas.tournament.models.viewModels.TournamentMatchUpResultViewModel;
import com.ipdweb.areas.tournament.models.viewModels.TournamentPreviewViewModel;
import com.ipdweb.areas.tournament.models.viewModels.TournamentResultViewModel;
import com.ipdweb.areas.user.entities.User;

import java.util.Set;

public interface TournamentService {

    void save(CreateTournamentBindingModel createTournamentBindingModel, User user);

    void edit(EditTournamentBindingModel editTournamentBindingModel);

    EditTournamentBindingModel getEditTournamentById(Long id);

    TournamentResultViewModel getTournamentResultViewById(Long id);

    Set<TournamentPreviewViewModel> getAllTournaments(User user);

    TournamentMatchUpResultViewModel getTournamentMatchUpResults(SelectMatchUpResultsBindingModel selectMatchUpResultsBindingModel);

    void deleteTournamentById(Long id);

    boolean ownsTournament(User user, Long tourId);

    void resetTournament(Long id);
}
