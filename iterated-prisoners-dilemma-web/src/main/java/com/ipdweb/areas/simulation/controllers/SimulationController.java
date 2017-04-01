package com.ipdweb.areas.simulation.controllers;

import com.ipdweb.areas.simulation.models.bindingModels.CreateSimulationBindingModel;
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

    @GetMapping("/delete/{id}")
    public String deleteTournament(@PathVariable long id) {
        this.simulationService.deleteSimulationById(id);

        return "redirect:/simulations";
    }




}
