package com.ipdweb.areas.simulation.controllers;

import com.google.gson.Gson;
import com.ipdweb.areas.common.exceptions.UnauthorizedAccessException;
import com.ipdweb.areas.simulation.exceptions.SimulationNotFoundException;
import com.ipdweb.areas.simulation.models.bindingModels.CreateSimulationBindingModel;
import com.ipdweb.areas.simulation.models.bindingModels.EditSimulationBindingModel;
import com.ipdweb.areas.simulation.models.bindingModels.RunMoreGenerationsBindingModel;
import com.ipdweb.areas.simulation.models.viewModels.SimulationResultViewModel;
import com.ipdweb.areas.simulation.services.SimulationService;
import com.ipdweb.areas.strategy.services.StrategyService;
import com.ipdweb.areas.user.entities.User;
import com.ipdweb.areas.user.exceptions.UserNotFoundException;
import com.ipdweb.areas.user.services.BasicUserService;
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

    private SimulationService simulationService;
    private StrategyService strategyService;
    private BasicUserService userService;

    @Autowired
    public SimulationController(SimulationService simulationService, StrategyService strategyService, BasicUserService userService) {
        this.simulationService = simulationService;
        this.strategyService = strategyService;
        this.userService = userService;
    }

    @GetMapping("")
    public String redirectToSimulationsPage(Authentication authentication) {
        User loggedUser = (User) authentication.getPrincipal();

        return "redirect:/simulations/" + loggedUser.getId();
    }

    @GetMapping("{userId}")
    public String getSimulationsPage(@PathVariable long userId, Model model) {
        User user = this.userService.getUserById(userId);

        model.addAttribute("userId", userId);
        model.addAttribute("simulations", this.simulationService.getAllSimulations(user));

        return "simulations/simulations-preview";
    }

    @GetMapping("/create")
    public String redirectToCreateSimulationPage(Authentication authentication) {
        User loggedUser = (User) authentication.getPrincipal();

        return "redirect:/simulations/" + loggedUser.getId() + "/create";
    }


    @GetMapping("/{userId}/create")
    public String getCreateSimulationPage(@PathVariable long userId, @ModelAttribute CreateSimulationBindingModel createSimulationBindingModel, Model model) {

        model.addAttribute("userId", userId);
        model.addAttribute("strategyMap", this.strategyService.getStrategyMap());

        return "simulations/simulations-create";
    }

    @PostMapping("/{userId}/create")
    public String createSimulation(@PathVariable long userId, @Valid @ModelAttribute CreateSimulationBindingModel createSimulationBindingModel, BindingResult bindingResult, Model model) {
        User user = this.userService.getUserById(userId);

        model.addAttribute("userId", userId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("strategyMap", this.strategyService.getStrategyMap());
            return "simulations/simulations-create";
        }

        this.simulationService.save(createSimulationBindingModel, user);

        return "redirect:/simulations/" + userId;
    }

    @GetMapping("/{userId}/show/{simId}")
    public String getSimulationResultPage(@PathVariable long userId, @PathVariable long simId, @ModelAttribute RunMoreGenerationsBindingModel runMoreGenerationsBindingModel, Model model, Authentication authentication) {
        User loggedUser = (User) authentication.getPrincipal();

        model.addAttribute("userId", userId);

        ///Throws exception if doesn't own. Exception redirects to error page
        this.simulationService.ownsSimulation(loggedUser, simId);

        SimulationResultViewModel simulationResultViewModel = this.simulationService.getSimulationResultViewById(simId);

        String strats = new Gson().toJson(simulationResultViewModel.getStrategyNames());
        String matrix = new Gson().toJson(simulationResultViewModel.getStrategyCounts());
        String genCount = new Gson().toJson(simulationResultViewModel.getGenerationCount());

        model.addAttribute("genCount", genCount);
        model.addAttribute("matrix", matrix);
        model.addAttribute("strats", strats);
        model.addAttribute("simId", simulationResultViewModel.getId());
        model.addAttribute("name", simulationResultViewModel.getName());

        return "simulations/simulations-show-result";
    }

    @PostMapping("/{userId}/show/{simId}")
    public String runMoreGenerationsResultPage(@PathVariable long userId, @PathVariable long simId, @Valid @ModelAttribute RunMoreGenerationsBindingModel runMoreGenerationsBindingModel, BindingResult bindingResult, Model model, Authentication authentication) {
        User loggedUser = (User) authentication.getPrincipal();

        model.addAttribute("loggedUserId", loggedUser.getId());
        model.addAttribute("userId", userId);

        ///Throws exception if doesn't own. Exception redirects to error page
        this.simulationService.ownsSimulation(loggedUser, simId);

        SimulationResultViewModel simulationResultViewModel = this.simulationService.getSimulationResultViewById(simId);

        String strats = new Gson().toJson(simulationResultViewModel.getStrategyNames());
        String matrix = new Gson().toJson(simulationResultViewModel.getStrategyCounts());
        String genCount = new Gson().toJson(simulationResultViewModel.getGenerationCount());

        model.addAttribute("genCount", genCount);
        model.addAttribute("matrix", matrix);
        model.addAttribute("strats", strats);
        model.addAttribute("simId", simulationResultViewModel.getId());
        model.addAttribute("name", simulationResultViewModel.getName());

        if (bindingResult.hasErrors()) {
            return "simulations/simulations-show-result";
        }

        runMoreGenerationsBindingModel.setId(simId);
        this.simulationService.runExtraGenerations(runMoreGenerationsBindingModel);

        return "redirect:/simulations/" + userId + "/show/" + simId;
    }

    @GetMapping("/{userId}/edit/{simId}")
    public String getEditSimulationPage(@PathVariable long userId, @PathVariable long simId, Model model, Authentication authentication) {
        User loggedUser = (User) authentication.getPrincipal();

        model.addAttribute("userId", userId);

        ///Throws exception if doesn't own. Exception redirects to error page
        this.simulationService.ownsSimulation(loggedUser, simId);

        model.addAttribute("editSimulationBindingModel", this.simulationService.getEditSimulationById(simId));
        return "simulations/simulations-edit";
    }

    @PostMapping("/{userId}/edit/{simId}")
    public String editTournament(@PathVariable long userId, @PathVariable long simId, @Valid @ModelAttribute EditSimulationBindingModel editSimulationBindingModel, BindingResult bindingResult, Model model, Authentication authentication) {

        User loggedUser = (User) authentication.getPrincipal();

        model.addAttribute("userId", userId);

        ///Throws exception if doesn't own. Exception redirects to error page
        this.simulationService.ownsSimulation(loggedUser, simId);

        if (bindingResult.hasErrors()) {
            return "simulations/simulations-edit";
        }
        editSimulationBindingModel.setId(simId);

        this.simulationService.edit(editSimulationBindingModel);

        return "redirect:/simulations/" + userId;
    }

    @GetMapping("/{userId}/delete/{simId}")
    public String deleteTournament(@PathVariable long userId, @PathVariable long simId, Model model, Authentication authentication) {
        User loggedUser = (User) authentication.getPrincipal();

        model.addAttribute("userId", userId);

        ///Throws exception if doesn't own. Exception redirects to error page
        this.simulationService.ownsSimulation(loggedUser, simId);

        this.simulationService.deleteSimulationById(simId);

        return "redirect:/simulations/" + userId;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String catchUserNotFoundException() {

        return "exceptions/user-not-found";
    }

    @ExceptionHandler(SimulationNotFoundException.class)
    public String catchSimulationNotFoundException() {

        return "exceptions/simulation-not-found";
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public String catchUnauthorizedAccessException() {

        return "exceptions/unauthorized-access";
    }
}
