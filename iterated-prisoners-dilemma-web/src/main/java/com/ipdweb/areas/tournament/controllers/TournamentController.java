package com.ipdweb.areas.tournament.controllers;

import com.google.gson.Gson;
import com.ipdweb.areas.strategy.services.StrategyService;
import com.ipdweb.areas.tournament.models.bindingModels.CreateTournamentBindingModel;
import com.ipdweb.areas.tournament.models.bindingModels.EditTournamentBindingModel;
import com.ipdweb.areas.tournament.models.viewModels.TournamentResultViewModel;
import com.ipdweb.areas.tournament.services.TournamentService;
import com.ipdweb.areas.user.entities.User;
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

    //@PreFilter(value = "Tournament.user = user")
    @GetMapping("")
    public String getTournamentsPage(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("tournaments", this.tournamentService.getAllTournaments(user));

        return "tournaments-preview";
    }

    @GetMapping("/show/{tourId}")
    public String getTournamentResultPage(@PathVariable long tourId, Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        //TODO add pop up
        if (!this.tournamentService.ownsTournament(user, tourId)) {
            return "redirect:/tournaments";
        }

        TournamentResultViewModel tournamentResultViewModel = this.tournamentService.getTournamentById(tourId);

        String data = new Gson().toJson(tournamentResultViewModel.getStrategyScoreKVPairs());

        model.addAttribute("data", data);

        return "tournaments-show-result";
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

    @GetMapping("/edit/{id}")
    public String getEditTournamentPage(@PathVariable long id, Model model) {

        model.addAttribute("editTournamentBindingModel", this.tournamentService.getEditTournamentById(id));
        return "tournaments-edit";
    }

    @PostMapping("/edit/{id}")
    public String editTournament(@PathVariable long id, @Valid @ModelAttribute EditTournamentBindingModel editTournamentBindingModel, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "tournaments-edit";
        }

        this.tournamentService.edit(editTournamentBindingModel);

        return "redirect:/tournaments";
    }


    @GetMapping("/delete/{id}")
    public String deleteTournament(@PathVariable long id) {
        this.tournamentService.deleteTournamentById(id);

        return "redirect:/tournaments";
    }


}
