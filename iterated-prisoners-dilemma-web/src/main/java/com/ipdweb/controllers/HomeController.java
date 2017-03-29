package com.ipdweb.controllers;

import com.ipdweb.areas.simulation.entities.Simulation;
import com.ipdweb.areas.tournament.entities.Tournament;
import com.ipdweb.areas.simulation.repositories.GenerationRepository;
import com.ipdweb.areas.simulation.services.SimulationService;
import com.ipdweb.areas.strategy.services.StrategyService;
import com.ipdweb.areas.tournament.services.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private SimulationService simulationService;

    @Autowired
    private GenerationRepository generationRepository;

    @Autowired
    private StrategyService strategyService;

    @GetMapping("/")
    public String getHomePage() {
        System.out.println();
        return "home";
    }



    @GetMapping("/tour/create")
    public String testCreateTournament() {

        Tournament tournament = new Tournament();
        tournament.setName("tour10");

        tournament.addStrategy(this.strategyService.getStrategyByName("TitForTat"));
        tournament.addStrategy(this.strategyService.getStrategyByName("Random"));
        tournament.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        tournament.addStrategy(this.strategyService.getStrategyByName("Grudger"));

        tournament.playOut();

        this.tournamentService.save(tournament);

        return "home";
    }

    @GetMapping("/tour/reset")
    public String testResetTournament() {
        this.tournamentService.resetTournament(9L);

        return "home";
    }


    @GetMapping("/tour/delete")
    public String testDeleteTournament() {
        this.tournamentService.deleteTournamentById(9L);

        return "home";
    }

    @GetMapping("/sim/reset")
    public String testResetSim() {
        this.simulationService.resetSimulation(3L);

        return "home";
    }

    @GetMapping("/sim/get")
    public String testSimReset() {
        Simulation sim = this.simulationService.getSimulationById(4L);
        System.out.println();
        return "home";
    }

    @GetMapping("/gen/get")
    public String testGetGeneration() {
//        Generation stuff = this.generationRepository.getGenerationResultsByGenerationId(20L);
        System.out.println();
        return "home";
    }

    @GetMapping("/sim/delete")
    public String testSimBroken() {
        this.simulationService.deleteSimulationById(7L);
        //Simulation sim = this.simulationService.getSimulationById(4l);
        System.out.println();
        return "home";
    }


    @GetMapping("/simulation/create")
    private String testSim() {
        Simulation simulation = new Simulation("Sim5");
        simulation.addStrategy(this.strategyService.getStrategyByName("TitForTat"));
        simulation.addStrategy(this.strategyService.getStrategyByName("TitForTat"));
        simulation.addStrategy(this.strategyService.getStrategyByName("TitForTat"));
        simulation.addStrategy(this.strategyService.getStrategyByName("TitForTat"));
        simulation.addStrategy(this.strategyService.getStrategyByName("TitForTat"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));
        simulation.addStrategy(this.strategyService.getStrategyByName("AlwaysDefect"));

        simulation.run(10);

        this.simulationService.save(simulation);

        return "home";
    }

    @GetMapping("/error")
    public String getErrorPage() {
        return "error";
    }
}
