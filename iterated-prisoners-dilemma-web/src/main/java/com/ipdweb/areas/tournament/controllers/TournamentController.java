package com.ipdweb.areas.tournament.controllers;

import com.ipdweb.areas.tournament.models.viewModels.TournamentResultViewModel;
import com.ipdweb.areas.tournament.services.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tournaments")
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;


    @GetMapping("")
    public String getTournamentsPage(Model model) {
        model.addAttribute("tournaments", this.tournamentService.getAllTournaments());

        return "tournaments-preview";
    }

    @GetMapping("/show/{tourId}")
    public String getTournamentResultPage(@PathVariable long tourId){
        TournamentResultViewModel tournamentResultViewModel = this.tournamentService.getTournamentById(tourId);


        return"tournaments-show-result";
    }



}
