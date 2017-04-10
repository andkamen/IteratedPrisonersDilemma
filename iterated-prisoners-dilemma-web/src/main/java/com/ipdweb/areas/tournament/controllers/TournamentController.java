package com.ipdweb.areas.tournament.controllers;

import com.google.gson.Gson;
import com.ipdweb.areas.strategy.services.StrategyService;
import com.ipdweb.areas.tournament.exceptions.TournamentNotFoundException;
import com.ipdweb.areas.tournament.models.bindingModels.CreateTournamentBindingModel;
import com.ipdweb.areas.tournament.models.bindingModels.EditTournamentBindingModel;
import com.ipdweb.areas.tournament.models.bindingModels.SelectMatchUpResultsBindingModel;
import com.ipdweb.areas.tournament.models.viewModels.TournamentMatchUpResultViewModel;
import com.ipdweb.areas.tournament.models.viewModels.TournamentResultViewModel;
import com.ipdweb.areas.tournament.services.TournamentService;
import com.ipdweb.areas.user.entities.User;
import com.ipdweb.areas.tournament.exceptions.UnauthorizedTournamentAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/tournaments")
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private StrategyService strategyService;

    @GetMapping("")
    public String getTournamentsPage(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("tournaments", this.tournamentService.getAllTournaments(user));

        return "tournaments-preview";
    }

    @GetMapping("/create")
    public String getCreateTournamentPage(@ModelAttribute CreateTournamentBindingModel createTournamentBindingModel, Model model) {

        model.addAttribute("strategyMap", this.strategyService.getStrategyMap());

        return "tournaments-create";
    }

    @PostMapping("/create")
    public String createTournament(@Valid @ModelAttribute CreateTournamentBindingModel createTournamentBindingModel, BindingResult bindingResult, Model model, Authentication authentication) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("strategyMap", this.strategyService.getStrategyMap());
            return "tournaments-create";
        }

        this.tournamentService.save(createTournamentBindingModel, (User) authentication.getPrincipal());

        return "redirect:/tournaments";
    }

    @GetMapping("/show/{tourId}")
    public String getTournamentResultPage(@PathVariable long tourId, @ModelAttribute SelectMatchUpResultsBindingModel selectMatchUpResultsBindingModel, Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        //Throws exception if doesn't own. Exception redirects to error page
        this.tournamentService.ownsTournament(user, tourId);

        TournamentResultViewModel tournamentResultViewModel = this.tournamentService.getTournamentResultViewById(tourId);

        String data = new Gson().toJson(tournamentResultViewModel.getStrategyScoreKVPairs());

        model.addAttribute("id",tournamentResultViewModel.getId());
        model.addAttribute("data", data);
        model.addAttribute("strategies", tournamentResultViewModel.getStrategies());

        return "tournaments-show-result";
    }

    //TODO code repetition? check if redirect is possible
    @PostMapping("/show/{tourId}")
    public String enhanceTournamentResultsPage(@PathVariable long tourId, @Valid @ModelAttribute SelectMatchUpResultsBindingModel selectMatchUpResultsBindingModel, BindingResult bindingResult, Model model, Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        //Throws exception if doesn't own. Exception redirects to error page
        this.tournamentService.ownsTournament(user, tourId);

        TournamentResultViewModel tournamentResultViewModel = this.tournamentService.getTournamentResultViewById(tourId);

        String data = new Gson().toJson(tournamentResultViewModel.getStrategyScoreKVPairs());

        model.addAttribute("id",tournamentResultViewModel.getId());
        model.addAttribute("data", data);
        model.addAttribute("strategies", tournamentResultViewModel.getStrategies());

        if (bindingResult.hasErrors()) {
            return "tournaments-show-result";
        }

        selectMatchUpResultsBindingModel.setId(tourId);
        TournamentMatchUpResultViewModel tournamentMatchUpResultViewModel = this.tournamentService.getTournamentMatchUpResults(selectMatchUpResultsBindingModel);

        model.addAttribute("tournamentMatchUpResultViewModel",tournamentMatchUpResultViewModel);

        return "tournaments-show-result";
    }

    @GetMapping("/edit/{id}")
    public String getEditTournamentPage(@PathVariable long id, Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        //Throws exception if doesn't own. Exception redirects to error page
        this.tournamentService.ownsTournament(user, id);

        model.addAttribute("editTournamentBindingModel", this.tournamentService.getEditTournamentById(id));
        return "tournaments-edit";
    }

    @PostMapping("/edit/{id}")
    public String editTournament(@PathVariable long id, @Valid @ModelAttribute EditTournamentBindingModel editTournamentBindingModel, BindingResult bindingResult, Model model, Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        //Throws exception if doesn't own. Exception redirects to error page
        this.tournamentService.ownsTournament(user, id);

        if (bindingResult.hasErrors()) {
            return "tournaments-edit";
        }

        this.tournamentService.edit(editTournamentBindingModel);

        return "redirect:/tournaments";
    }


    @GetMapping("/delete/{id}")
    public String deleteTournament(@PathVariable long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        ///Throws exception if doesn't own. Exception redirects to error page
        this.tournamentService.ownsTournament(user, id);

        this.tournamentService.deleteTournamentById(id);

        return "redirect:/tournaments";
    }


    @ExceptionHandler(TournamentNotFoundException.class)
    public String catchTournamentNotFoundException() {

        return "exceptions/tournament-not-found";
    }

    @ExceptionHandler(UnauthorizedTournamentAccessException.class)
    public String catchUnauthorizedTournamentAccessException() {

        return "exceptions/unauthorized-tournament-access";
    }

}
