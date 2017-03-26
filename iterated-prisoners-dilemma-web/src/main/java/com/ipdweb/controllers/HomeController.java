package com.ipdweb.controllers;

import com.ipdweb.entities.Simulation;
import com.ipdweb.entities.Tournament;
import com.ipdweb.repositories.SimulationRepository;
import com.ipdweb.services.SimulationService;
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
    private SimulationService simulationService;

    //TODO create service
    @Autowired
    private SimulationRepository simulationRepository;

    @Autowired
    private StrategyService strategyService;


    @GetMapping("/")
    public String getHomePage() {

        Tournament tournament = new Tournament();
        tournament.setName("test");

        tournament.addStrategy(this.strategyService.getStrategyByName("TitForTat"));
        tournament.addStrategy(this.strategyService.getStrategyByName("Random"));
        tournament.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        tournament.addStrategy(this.strategyService.getStrategyByName("Grudger"));

        tournament.playOut();

        this.tournamentService.save(tournament);

        return "home";
    }

    @GetMapping("/reset")
    public String testResetTournament() {
        this.tournamentService.resetTournament(5l);

        return "home";
    }

    @GetMapping("/sim_reset")
    public String testSimReset() {
        Simulation sim = this.simulationService.getSimulationById(4l);
        System.out.println();
        return "home";
    }


    @GetMapping("/simulation")
    private String testSim() {
        Simulation simulation = new Simulation("Sim3");
        simulation.addStrategy(this.strategyService.getStrategyByName("TitForTat"));
        simulation.addStrategy(this.strategyService.getStrategyByName("TitForTat"));
        simulation.addStrategy(this.strategyService.getStrategyByName("TitForTat"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));

        simulation.run(5);

        this.simulationService.save(simulation);

        return "home";
    }

    @GetMapping("/error")
    public String getErrorPage() {
        return "error";
    }
}
