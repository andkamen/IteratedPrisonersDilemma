package com.ipdweb.controllers;

import com.ipdweb.entities.Tournament;
import com.ipdweb.services.StrategyService;
import com.ipdweb.services.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    StrategyService strategyService;


    @GetMapping("/")
    public String getHomePage() {

        Tournament tournament = new Tournament();
        tournament.setName("test");

        tournament.addStrategy(this.strategyService.getStrategyByName("TitForTat"));
        tournament.addStrategy(this.strategyService.getStrategyByName("TitForTat"));
        tournament.addStrategy(this.strategyService.getStrategyByName("TitForTat"));
        tournament.addStrategy(this.strategyService.getStrategyByName("TitForTat"));

        tournament.playOut();

        this.tournamentService.save(tournament);

        return "home";
    }

    @GetMapping("/error")
    public String getErrorPage() {
        return "error";
    }
}
