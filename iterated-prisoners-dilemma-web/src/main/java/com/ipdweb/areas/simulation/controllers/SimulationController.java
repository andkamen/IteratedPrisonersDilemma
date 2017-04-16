package com.ipdweb.areas.simulation.controllers;

import com.google.gson.Gson;
import com.ipdweb.areas.simulation.exceptions.SimulationNotFoundException;
import com.ipdweb.areas.common.exceptions.UnauthorizedAccessException;
import com.ipdweb.areas.simulation.models.bindingModels.CreateSimulationBindingModel;
import com.ipdweb.areas.simulation.models.bindingModels.EditSimulationBindingModel;
import com.ipdweb.areas.simulation.models.bindingModels.RunMoreGenerationsBindingModel;
import com.ipdweb.areas.simulation.models.viewModels.SimulationResultViewModel;
import com.ipdweb.areas.simulation.services.SimulationService;
import com.ipdweb.areas.strategy.services.StrategyService;
import com.ipdweb.areas.user.entities.Role;
import com.ipdweb.areas.user.entities.User;
import com.ipdweb.areas.user.exceptions.UserNotFoundException;
import com.ipdweb.areas.user.services.BasicUserService;
import com.ipdweb.areas.user.services.FacebookUserService;
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

    @Autowired
    private BasicUserService basicUserService;

    @Autowired
    private FacebookUserService facebookUserService;

    @GetMapping("{userId}")
    public String getSimulationsPage(@PathVariable long userId, Model model, Authentication authentication) {
        User loggedUser = (User) authentication.getPrincipal();
        User user = getUser(userId, loggedUser);

        model.addAttribute("loggedUserId", loggedUser.getId());
        model.addAttribute("userId", userId);
        model.addAttribute("simulations", this.simulationService.getAllSimulations(user));

        return "simulations-preview";
    }

    @GetMapping("/{userId}/create")
    public String getCreateSimulationPage(@PathVariable long userId, @ModelAttribute CreateSimulationBindingModel createSimulationBindingModel, Model model, Authentication authentication) {
        User loggedUser = (User) authentication.getPrincipal();

        model.addAttribute("loggedUserId", loggedUser.getId());
        model.addAttribute("userId", userId);

        model.addAttribute("strategyMap", this.strategyService.getStrategyMap());

        return "simulations-create";
    }

    @PostMapping("/{userId}/create")
    public String createSimulation(@PathVariable long userId, @Valid @ModelAttribute CreateSimulationBindingModel createSimulationBindingModel, BindingResult bindingResult, Model model, Authentication authentication) {
        User loggedUser = (User) authentication.getPrincipal();

        model.addAttribute("loggedUserId", loggedUser.getId());
        model.addAttribute("userId", userId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("strategyMap", this.strategyService.getStrategyMap());
            return "simulations-create";
        }

        this.simulationService.save(createSimulationBindingModel, getUser(userId, loggedUser));

        return "redirect:/simulations/" + userId;
    }

    @GetMapping("/{userId}/show/{simId}")
    public String getSimulationResultPage(@PathVariable long userId, @PathVariable long simId, @ModelAttribute RunMoreGenerationsBindingModel runMoreGenerationsBindingModel, Model model, Authentication authentication) {
        User loggedUser = (User) authentication.getPrincipal();

        model.addAttribute("loggedUserId", loggedUser.getId());
        model.addAttribute("userId", userId);

        ///Throws exception if doesn't own. Exception redirects to error page
        this.simulationService.ownsSimulation(loggedUser, simId);

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

        return "redirect:/simulations/" + userId + "/show/" + simId;
    }

    @GetMapping("/{userId}/edit/{simId}")
    public String getEditSimulationPage(@PathVariable long userId, @PathVariable long simId, Model model, Authentication authentication) {
        User loggedUser = (User) authentication.getPrincipal();

        model.addAttribute("loggedUserId", loggedUser.getId());
        model.addAttribute("userId", userId);

        ///Throws exception if doesn't own. Exception redirects to error page
        this.simulationService.ownsSimulation(loggedUser, simId);

        model.addAttribute("editSimulationBindingModel", this.simulationService.getEditSimulationById(simId));
        return "simulations-edit";
    }

    @PostMapping("/{userId}/edit/{simId}")
    public String editTournament(@PathVariable long userId, @PathVariable long simId, @Valid @ModelAttribute EditSimulationBindingModel editSimulationBindingModel, BindingResult bindingResult, Model model, Authentication authentication) {

        User loggedUser = (User) authentication.getPrincipal();

        model.addAttribute("loggedUserId", loggedUser.getId());
        model.addAttribute("userId", userId);

        ///Throws exception if doesn't own. Exception redirects to error page
        this.simulationService.ownsSimulation(loggedUser, simId);

        if (bindingResult.hasErrors()) {
            return "simulations-edit";
        }
        editSimulationBindingModel.setId(simId);

        this.simulationService.edit(editSimulationBindingModel);

        return "redirect:/simulations/" + userId;
    }

    @GetMapping("/{userId}/delete/{simId}")
    public String deleteTournament(@PathVariable long userId, @PathVariable long simId, Model model, Authentication authentication) {
        User loggedUser = (User) authentication.getPrincipal();

        model.addAttribute("loggedUserId", loggedUser.getId());
        model.addAttribute("userId", userId);

        ///Throws exception if doesn't own. Exception redirects to error page
        this.simulationService.ownsSimulation(loggedUser, simId);

        this.simulationService.deleteSimulationById(simId);

        return "redirect:/simulations/" + loggedUser.getId();
    }

    @ExceptionHandler(SimulationNotFoundException.class)
    public String catchSimulationNotFoundException(Model model, Authentication authentication) {
        User loggedUser = (User) authentication.getPrincipal();

        model.addAttribute("loggedUserId", loggedUser.getId());
        model.addAttribute("userId", loggedUser.getId());

        return "exceptions/simulation-not-found";
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public String catchUnauthorizedAccessException(Model model, Authentication authentication) {
        User loggedUser = (User) authentication.getPrincipal();

        model.addAttribute("loggedUserId", loggedUser.getId());
        model.addAttribute("userId", loggedUser.getId());

        return "exceptions/unauthorized-access";
    }

    private User getUser(long id, User loggedUser) {
        User user = this.basicUserService.getUserById(id);

        if (user == null) {
            user = this.facebookUserService.getUserById(id);
            if (user == null) {
                throw new UserNotFoundException();
            }
        }

        boolean isAdmin = false;

        for (Role role : loggedUser.getAuthorities()) {
            if (role.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }
        }

        if ((user.getId() != loggedUser.getId()) && !isAdmin) {
            throw new UnauthorizedAccessException();
        }

        return user;
    }
}
