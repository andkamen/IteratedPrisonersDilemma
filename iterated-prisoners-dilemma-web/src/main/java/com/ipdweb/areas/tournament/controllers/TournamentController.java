package com.ipdweb.areas.tournament.controllers;

import com.google.gson.Gson;
import com.ipdweb.areas.strategy.services.StrategyService;
import com.ipdweb.areas.tournament.models.bindingModels.CreateTournamentBindingModel;
import com.ipdweb.areas.tournament.models.viewModels.TournamentResultViewModel;
import com.ipdweb.areas.tournament.services.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String getTournamentsPage(Model model) {
        model.addAttribute("tournaments", this.tournamentService.getAllTournaments());

        return "tournaments-preview";
    }

    @GetMapping("/show/{tourId}")
    public String getTournamentResultPage(@PathVariable long tourId, Model model) {
        TournamentResultViewModel tournamentResultViewModel = this.tournamentService.getTournamentById(tourId);

        String data = new Gson().toJson(tournamentResultViewModel.getStrategiesMap());

        model.addAttribute("data", data);

        return "tournaments-show-result";
    }

    @GetMapping("/create")
    public String getCreateTournamentPage(@ModelAttribute CreateTournamentBindingModel createTournamentBindingModel, Model model) {

        model.addAttribute("strategyMap", this.strategyService.getStrategyMap());

        return "tournaments-create";
    }

    @PostMapping("/create")
    public String createTournament(@Valid @ModelAttribute CreateTournamentBindingModel createTournamentBindingModel, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("strategyMap", this.strategyService.getStrategyMap());
            return "tournaments-create";
        }

        this.tournamentService.save(createTournamentBindingModel);

        return "redirect:/tournaments";
    }

    @GetMapping("/delete/{id}")
    public String deleteTournament(@PathVariable long id) {
        this.tournamentService.deleteTournamentById(id);

        return "redirect:/tournaments";
    }


}
