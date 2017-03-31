package com.ipdweb.areas.tournament.services;

import com.ipdweb.areas.tournament.models.bindingModels.CreateTournamentBindingModel;
import com.ipdweb.areas.tournament.models.bindingModels.EditTournamentBindingModel;
import com.ipdweb.areas.tournament.models.viewModels.TournamentPreviewViewModel;
import com.ipdweb.areas.tournament.models.viewModels.TournamentResultViewModel;
import com.ipdweb.areas.user.entities.User;

import java.util.Set;

public interface TournamentService {

    void save(CreateTournamentBindingModel createTournamentBindingModel, User user);

    void edit(EditTournamentBindingModel editTournamentBindingModel);

    void resetTournament(Long id);

    void deleteTournamentById(Long id);

    TournamentResultViewModel getTournamentById(Long id);

    EditTournamentBindingModel getEditTournamentById(Long id);

    Set<TournamentPreviewViewModel> getAllTournaments(User user);

    boolean ownsTournament(User user, Long tourId);

    // tournament getTournamentById(Long id);
}
