package com.ipdweb.areas.tournament.servicesImpl;


import com.ipdweb.areas.tournament.entities.Tournament;
import com.ipdweb.areas.tournament.entities.TournamentMatchUpResult;
import com.ipdweb.areas.tournament.models.viewModels.TournamentPreviewViewModel;
import com.ipdweb.areas.tournament.models.viewModels.TournamentResultViewModel;
import com.ipdweb.areas.tournament.repositories.TournamentRepository;
import com.ipdweb.areas.tournament.services.TournamentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class TournamentServiceImpl implements TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void save(Tournament tournament) {
        this.tournamentRepository.save(tournament);
    }

    @Override
    public void resetTournament(Long id) {
        Tournament tournament = this.tournamentRepository.getTournamentById(id);
        tournament.getTournamentMatchUpResults().clear();

        this.tournamentRepository.save(tournament);
    }

    @Override
    public void deleteTournamentById(Long id) {
        this.tournamentRepository.delete(id);
    }

    @Override
    public TournamentResultViewModel getTournamentById(Long id) {
        Tournament tournamentDB = this.tournamentRepository.getTournamentById(id);
        TournamentResultViewModel tournament = this.modelMapper.map(tournamentDB, TournamentResultViewModel.class);

        for (int i = 0; i < tournament.getStrategies().size(); i++) {
            tournament.getStrategyScores().add(0);
        }

        List<TournamentMatchUpResult> tournamentMatchUpResults = tournamentDB.getTournamentMatchUpResults();
        if (tournamentMatchUpResults.isEmpty()) {
            return tournament;
        }
        int index = 0;
        int strategyScore = 0;
        for (int a = 0; a < tournament.getStrategies().size(); a++) {
            for (int b = 0; b < tournament.getStrategies().size(); b++) {
                if (b <= a) {
                    continue;
                }
                TournamentMatchUpResult tournamentMatchUpResult = tournamentMatchUpResults.get(index++);

                strategyScore = tournament.getStrategyScores().get(a) + tournamentMatchUpResult.getStratAScore();
                tournament.getStrategyScores().set(a, strategyScore);

                strategyScore = tournament.getStrategyScores().get(b) + tournamentMatchUpResult.getStratBScore();
                tournament.getStrategyScores().set(b, strategyScore);
            }
        }

        return tournament;
    }

    @Override
    public Set<TournamentPreviewViewModel> getAllTournaments() {

        Set<Tournament> allTournaments = this.tournamentRepository.getAllTournaments();
        Set<TournamentPreviewViewModel> tournaments = new LinkedHashSet<>();

        for (Tournament tour : allTournaments) {

            TournamentPreviewViewModel tournament = this.modelMapper.map(tour, TournamentPreviewViewModel.class);
            tournaments.add(tournament);
        }

        return tournaments;
    }
}
