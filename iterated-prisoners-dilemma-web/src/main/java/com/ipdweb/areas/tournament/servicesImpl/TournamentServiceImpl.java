package com.ipdweb.areas.tournament.servicesImpl;


import com.ipdweb.areas.common.exceptions.UnauthorizedAccessException;
import com.ipdweb.areas.strategy.entities.StrategyImpl;
import com.ipdweb.areas.strategy.factories.StrategyFactory;
import com.ipdweb.areas.strategy.factories.StrategyFactoryImpl;
import com.ipdweb.areas.strategy.models.viewModels.StrategyKeyValueViewModel;
import com.ipdweb.areas.strategy.models.viewModels.StrategyMapViewModel;
import com.ipdweb.areas.strategy.services.StrategyService;
import com.ipdweb.areas.tournament.entities.Tournament;
import com.ipdweb.areas.tournament.entities.TournamentMatchUpResult;
import com.ipdweb.areas.tournament.exceptions.TournamentNotFoundException;
import com.ipdweb.areas.tournament.models.bindingModels.CreateTournamentBindingModel;
import com.ipdweb.areas.tournament.models.bindingModels.EditTournamentBindingModel;
import com.ipdweb.areas.tournament.models.bindingModels.SelectMatchUpResultsBindingModel;
import com.ipdweb.areas.tournament.models.viewModels.TournamentMatchUpResultViewModel;
import com.ipdweb.areas.tournament.models.viewModels.TournamentPreviewViewModel;
import com.ipdweb.areas.tournament.models.viewModels.TournamentResultViewModel;
import com.ipdweb.areas.tournament.repositories.TournamentRepository;
import com.ipdweb.areas.tournament.services.TournamentService;
import com.ipdweb.areas.user.entities.Role;
import com.ipdweb.areas.user.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TournamentServiceImpl implements TournamentService {

    private TournamentRepository tournamentRepository;
    private StrategyService strategyService;
    private ModelMapper modelMapper;

    @Autowired
    public TournamentServiceImpl(TournamentRepository tournamentRepository, StrategyService strategyService, ModelMapper modelMapper) {
        this.tournamentRepository = tournamentRepository;
        this.strategyService = strategyService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(CreateTournamentBindingModel createTournamentBindingModel, User user) {

        List<StrategyImpl> strategies = this.strategyService.getAllStrategyImpls();
        StrategyFactory strategyFactory = new StrategyFactoryImpl();

        Tournament tournament = this.modelMapper.map(createTournamentBindingModel, Tournament.class);
        tournament.setUser(user);

        for (Map.Entry<String, Integer> entry : createTournamentBindingModel.getStrategies().entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                StrategyImpl strategy = strategyFactory.hackStrategy(entry.getKey(), strategies);
                tournament.addStrategy(strategy);
            }
        }

        tournament.playOut();

        this.tournamentRepository.save(tournament);
    }

    @Override
    public void edit(EditTournamentBindingModel editTournamentBindingModel) {
        List<StrategyImpl> strategies = this.strategyService.getAllStrategyImpls();
        StrategyFactory strategyFactory = new StrategyFactoryImpl();

        //Reset Tournament Strategies and their results because they are no longer relevant
        Tournament tournament = this.tournamentRepository.getTournamentById(editTournamentBindingModel.getId());
        tournament.getStrategies().clear();
        tournament.getTournamentMatchUpResults().clear();
        tournament.setName(editTournamentBindingModel.getName());

        for (Map.Entry<String, Integer> entry : editTournamentBindingModel.getStrategies().entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                StrategyImpl strategy = strategyFactory.hackStrategy(entry.getKey(), strategies);
                tournament.addStrategy(strategy);
            }
        }

        tournament.playOut();
        this.tournamentRepository.save(tournament);
    }

    @Override
    public EditTournamentBindingModel getEditTournamentById(Long id) {
        //get currently registered strategies
        Tournament tournamentDB = this.tournamentRepository.getTournamentById(id);
        if (tournamentDB == null) {
            throw new TournamentNotFoundException();
        }

        //get a map with all basic strategies and a count of 0
        StrategyMapViewModel strategyMap = this.strategyService.getStrategyMap();
        EditTournamentBindingModel editTournament = this.modelMapper.map(tournamentDB, EditTournamentBindingModel.class);

        Map<String, Integer> strategies = strategyMap.getStrategies();

        //make the strategy map represent total number of strats registered in the tournament
        for (StrategyImpl strategy : tournamentDB.getStrategies()) {
            if (strategies.containsKey(strategy.getName())) {
                int count = strategies.get(strategy.getName()) + 1;
                strategies.put(strategy.getName(), count);
            }
        }

        editTournament.setStrategies(strategies);

        return editTournament;
    }

    @Override
    public TournamentResultViewModel getTournamentResultViewById(Long id) {
        Tournament tournamentFilled = getFullTournamentById(id);
        TournamentResultViewModel tournament = this.modelMapper.map(tournamentFilled, TournamentResultViewModel.class);

        List<StrategyKeyValueViewModel> sortedScores = new ArrayList<>();
        //fill scores
        for (int i = 0; i < tournament.getStrategies().size(); i++) {
            sortedScores.add(new StrategyKeyValueViewModel(
                    tournament.getStrategies().get(i).getName(),
                    tournament.getStrategyScores().get(i)));
        }
        //sort by score value
        sortedScores = sortedScores.stream().sorted((s1, s2) -> s2.getScore().compareTo(s1.getScore())).collect(Collectors.toList());
        tournament.setStrategyScoreKVPairs(sortedScores);

        return tournament;
    }

    @Override
    public Set<TournamentPreviewViewModel> getAllTournaments(User user) {

        Set<Tournament> allTournaments = this.tournamentRepository.getAllTournaments(user);
        Set<TournamentPreviewViewModel> tournaments = new LinkedHashSet<>();

        for (Tournament tour : allTournaments) {
            TournamentPreviewViewModel tournament = this.modelMapper.map(tour, TournamentPreviewViewModel.class);
            tournaments.add(tournament);
        }

        return tournaments;
    }

    @Override
    public TournamentMatchUpResultViewModel getTournamentMatchUpResults(SelectMatchUpResultsBindingModel selectMatchUpResultsBindingModel) {
        Tournament tournament = this.tournamentRepository.getTournamentById(selectMatchUpResultsBindingModel.getId());

        if (selectMatchUpResultsBindingModel.isDualFilter()) {
            tournament = this.getDoubleFilteredTournament(tournament, selectMatchUpResultsBindingModel.getStrategyMatchUps());
        } else {
            tournament = this.getSingleFilteredTournament(tournament, selectMatchUpResultsBindingModel.getStrategyMatchUps());
        }

        TournamentMatchUpResultViewModel tournamentMatchUpResultViewModel = this.modelMapper.map(tournament, TournamentMatchUpResultViewModel.class);

        return tournamentMatchUpResultViewModel;
    }


    @Override
    public void deleteTournamentById(Long id) {
        this.tournamentRepository.delete(id);
    }

    @Override
    public boolean ownsTournament(User user, Long id) {
        Tournament tournament = this.tournamentRepository.getTournamentById(id);
        if (tournament == null) {
            throw new TournamentNotFoundException();
        }

        for (Role role : user.getAuthorities()) {
            if (role.getAuthority().equals("ROLE_ADMIN")) {
                return true;
            }
        }

        if (tournament.getUser().getId() != user.getId()) {
            throw new UnauthorizedAccessException();
        }

        return true;
    }

    @Override
    public void resetTournament(Long id) {
        Tournament tournament = this.tournamentRepository.getTournamentById(id);
        if (tournament == null) {
            throw new TournamentNotFoundException();
        }
        tournament.getTournamentMatchUpResults().clear();

        this.tournamentRepository.save(tournament);
    }

    private Tournament getFullTournamentById(Long id) {
        Tournament tournament = this.tournamentRepository.getTournamentById(id);
        if (tournament == null) {
            throw new TournamentNotFoundException();
        }

        for (int i = 0; i < tournament.getStrategies().size(); i++) {
            tournament.getStrategyScores().add(0);
        }

        List<TournamentMatchUpResult> tournamentMatchUpResults = tournament.getTournamentMatchUpResults();
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

    private Tournament getDoubleFilteredTournament(Tournament tournament, String[] strategies) {
        Iterator iter = tournament.getTournamentMatchUpResults().iterator();
        while (iter.hasNext()) {
            TournamentMatchUpResult tournamentMatchUpResult = (TournamentMatchUpResult) iter.next();
            boolean foundMatch = false;

            OUTER_LOOP:
            for (int i = 0; i < strategies.length; i++) {
                for (int j = i + 1; j < strategies.length; j++) {
                    boolean stratAMatch = (tournamentMatchUpResult.getStratAName().equals(strategies[i]) ||
                            tournamentMatchUpResult.getStratAName().equals(strategies[j]));
                    boolean stratBMatch = (tournamentMatchUpResult.getStratBName().equals(strategies[i]) ||
                            tournamentMatchUpResult.getStratBName().equals(strategies[j]));

                    if (stratAMatch && stratBMatch) {
                        foundMatch = true;
                        break OUTER_LOOP;
                    }
                }
            }

            if (!foundMatch) {
                iter.remove();
            }
        }
        return tournament;
    }

    private Tournament getSingleFilteredTournament(Tournament tournament, String[] strategies) {
        Iterator iter = tournament.getTournamentMatchUpResults().iterator();
        while (iter.hasNext()) {
            TournamentMatchUpResult tournamentMatchUpResult = (TournamentMatchUpResult) iter.next();
            boolean foundMatch = false;

            for (String strategy : strategies) {
                if (tournamentMatchUpResult.getStratAName().equals(strategy) || tournamentMatchUpResult.getStratBName().equals(strategy)) {
                    foundMatch = true;
                    break;
                }
            }

            if (!foundMatch) {
                iter.remove();
            }
        }

        return tournament;
    }
}