package com.ipdweb.areas.simulation.controllers;

import com.google.gson.Gson;
import com.ipdweb.areas.simulation.models.bindingModels.CreateSimulationBindingModel;
import com.ipdweb.areas.simulation.models.bindingModels.EditSimulationBindingModel;
import com.ipdweb.areas.simulation.services.SimulationService;
import com.ipdweb.areas.strategy.services.StrategyService;
import com.ipdweb.areas.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/simulations")
public class SimulationController {

    @Autowired
    private SimulationService simulationService;

    @Autowired
    private StrategyService strategyService;

    @GetMapping("")
    public String getSimulationsPage(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        model.addAttribute("simulations", this.simulationService.getAllSimulations(user));

        return "simulations-preview";
    }

    @GetMapping("/create")
    public String getCreateSimulationPage(@ModelAttribute CreateSimulationBindingModel createSimulationBindingModel, Model model) {

        model.addAttribute("strategyMap", this.strategyService.getStrategyMap());

        return "simulations-create";
    }

    @PostMapping("/create")
    public String createSimulation(@Valid @ModelAttribute CreateSimulationBindingModel createSimulationBindingModel, BindingResult bindingResult, Model model, Authentication authentication) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("strategyMap", this.strategyService.getStrategyMap());
            return "simulations-create";
        }

        this.simulationService.save(createSimulationBindingModel, (User) authentication.getPrincipal());

        return "redirect:/simulations";
    }


    @GetMapping("/show/{simId}")
    public String getSimulationResultPage(@PathVariable long simId, Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        //TODO add pop up
        if (!this.simulationService.ownsSimulation(user, simId)) {
            return "redirect:/tournaments";
        }

        List<String> firstGenStrats = new ArrayList<>();
        firstGenStrats.add("Generation");
        firstGenStrats.add("Strat 1");
        firstGenStrats.add("Strat 2");
        firstGenStrats.add("Strat 3");


        int[][] matrix = new int[5][4];
        matrix[0] = new int[]{0,10,5,10};
        matrix[1] = new int[]{1,3,16,15};
        matrix[2] = new int[]{2,8,2,7};
        matrix[3] = new int[]{3,8,2,7};
        matrix[4] = new int[]{4,8,2,7};


//        TournamentResultViewModel tournamentResultViewModel = this.tournamentService.getTournamentById(simId);

        String strats = new Gson().toJson(firstGenStrats);
        String matrix1 = new Gson().toJson(matrix);

        model.addAttribute("matrix", matrix1);
        model.addAttribute("strats", strats);

        return "simulations-show-result";
    }

    @GetMapping("/edit/{id}")
    public String getEditSimulationPage(@PathVariable long id, Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        //TODO add pop up
        if (!this.simulationService.ownsSimulation(user, id)) {
            return "redirect:/simulations";
        }
        model.addAttribute("editSimulationBindingModel", this.simulationService.getEditSimulationById(id));
        return "simulations-edit";
    }

    @PostMapping("/edit/{id}")
    public String editTournament(@PathVariable long id, @Valid @ModelAttribute EditSimulationBindingModel editSimulationBindingModel, BindingResult bindingResult, Model model, Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        //TODO add pop up
        if (!this.simulationService.ownsSimulation(user, id)) {
            return "redirect:/simulations";
        }

        if (bindingResult.hasErrors()) {
            return "simulations-edit";
        }

        this.simulationService.edit(editSimulationBindingModel);

        return "redirect:/simulations";
    }

    @GetMapping("/delete/{id}")
    public String deleteTournament(@PathVariable long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        //TODO add pop up
        if (!this.simulationService.ownsSimulation(user, id)) {
            return "redirect:/simulations";
        }

        this.simulationService.deleteSimulationById(id);

        return "redirect:/simulations";
    }


}
