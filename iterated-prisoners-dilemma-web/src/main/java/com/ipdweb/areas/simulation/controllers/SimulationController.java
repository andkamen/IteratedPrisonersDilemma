package com.ipdweb.areas.simulation.controllers;

import com.google.gson.Gson;
import com.ipdweb.areas.simulation.exceptions.SimulationNotFoundException;
import com.ipdweb.areas.simulation.exceptions.UnauthorizedSimulationAccessException;
import com.ipdweb.areas.simulation.models.bindingModels.CreateSimulationBindingModel;
import com.ipdweb.areas.simulation.models.bindingModels.EditSimulationBindingModel;
import com.ipdweb.areas.simulation.models.bindingModels.RunMoreGenerationsBindingModel;
import com.ipdweb.areas.simulation.models.viewModels.SimulationResultViewModel;
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

    @GetMapping("/show/{simId}")
    public String getSimulationResultPage(@PathVariable long simId, @ModelAttribute RunMoreGenerationsBindingModel runMoreGenerationsBindingModel, Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        ///Throws exception if doesn't own. Exception redirects to error page
        this.simulationService.ownsSimulation(user, simId);

        SimulationResultViewModel simulationResultViewModel = this.simulationService.getSimulationResultViewById(simId);

        String strats = new Gson().toJson(simulationResultViewModel.getStrategyNames());
        String matrix = new Gson().toJson(simulationResultViewModel.getStrategyCounts());
        String genCount = new Gson().toJson(simulationResultViewModel.getGenerationCount());


        //TODO check if ok or should be split so model gets entire view and not it's fields
        model.addAttribute("genCount", genCount);
        model.addAttribute("matrix", matrix);
        model.addAttribute("strats", strats);
        model.addAttribute("id", simulationResultViewModel.getId());
        model.addAttribute("name", simulationResultViewModel.getName());

        return "simulations-show-result";
    }

    @PostMapping("/show/{simId}")
    public String runMoreGenerationsResultPage(@PathVariable long simId, @Valid @ModelAttribute RunMoreGenerationsBindingModel runMoreGenerationsBindingModel, BindingResult bindingResult, Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        ///Throws exception if doesn't own. Exception redirects to error page
        this.simulationService.ownsSimulation(user, simId);

        SimulationResultViewModel simulationResultViewModel = this.simulationService.getSimulationResultViewById(simId);

        String strats = new Gson().toJson(simulationResultViewModel.getStrategyNames());
        String matrix = new Gson().toJson(simulationResultViewModel.getStrategyCounts());
        String genCount = new Gson().toJson(simulationResultViewModel.getGenerationCount());

        //TODO check if ok or should be split so model gets entire view and not it's fields
        model.addAttribute("genCount", genCount);
        model.addAttribute("matrix", matrix);
        model.addAttribute("strats", strats);
        model.addAttribute("id", simulationResultViewModel.getId());
        model.addAttribute("name", simulationResultViewModel.getName());

        if (bindingResult.hasErrors()) {
            return "simulations-show-result";
        }
        runMoreGenerationsBindingModel.setId(simId);
        this.simulationService.runExtraGenerations(runMoreGenerationsBindingModel);

        return "redirect:/simulations/show/" + simId;
    }

    @GetMapping("/edit/{id}")
    public String getEditSimulationPage(@PathVariable long id, Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        ///Throws exception if doesn't own. Exception redirects to error page
        this.simulationService.ownsSimulation(user, id);

        model.addAttribute("editSimulationBindingModel", this.simulationService.getEditSimulationById(id));
        return "simulations-edit";
    }

    @PostMapping("/edit/{id}")
    public String editTournament(@PathVariable long id, @Valid @ModelAttribute EditSimulationBindingModel editSimulationBindingModel, BindingResult bindingResult, Model model, Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        ///Throws exception if doesn't own. Exception redirects to error page
        this.simulationService.ownsSimulation(user, id);

        if (bindingResult.hasErrors()) {
            return "simulations-edit";
        }

        this.simulationService.edit(editSimulationBindingModel);

        return "redirect:/simulations";
    }

    @GetMapping("/delete/{id}")
    public String deleteTournament(@PathVariable long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        ///Throws exception if doesn't own. Exception redirects to error page
        this.simulationService.ownsSimulation(user, id);

        this.simulationService.deleteSimulationById(id);

        return "redirect:/simulations";
    }

    @ExceptionHandler(SimulationNotFoundException.class)
    public String catchSimulationNotFoundException() {

        return "exceptions/simulation-not-found";
    }

    @ExceptionHandler(UnauthorizedSimulationAccessException.class)
    public String catchUnauthorizedSimulationAccessException() {

        return "exceptions/unauthorized-simulation-access";
    }
}
