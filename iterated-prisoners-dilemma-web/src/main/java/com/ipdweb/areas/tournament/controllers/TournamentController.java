package com.ipdweb.areas.tournament.controllers;

import com.google.gson.Gson;
import com.ipdweb.areas.common.exceptions.UnauthorizedAccessException;
import com.ipdweb.areas.common.utils.Constants;
import com.ipdweb.areas.strategy.services.StrategyService;
import com.ipdweb.areas.tournament.exceptions.TournamentNotFoundException;
import com.ipdweb.areas.tournament.models.bindingModels.CreateTournamentBindingModel;
import com.ipdweb.areas.tournament.models.bindingModels.EditTournamentBindingModel;
import com.ipdweb.areas.tournament.models.bindingModels.SelectMatchUpResultsBindingModel;
import com.ipdweb.areas.tournament.models.viewModels.TournamentMatchUpResultViewModel;
import com.ipdweb.areas.tournament.models.viewModels.TournamentResultViewModel;
import com.ipdweb.areas.tournament.services.TournamentService;
import com.ipdweb.areas.user.entities.Role;
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
@RequestMapping("/tournaments")
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private StrategyService strategyService;

    @Autowired
    private BasicUserService userService;

    //TODO make interceptor for this redirect
    @GetMapping("")
    public String redirectToTournamentsPage(Authentication authentication) {
        User loggedUser = (User) authentication.getPrincipal();

        return "redirect:/tournaments/" + loggedUser.getId();
    }


    @GetMapping("/{userId}")
    public String getTournamentsPage(@PathVariable long userId, Model model, Authentication authentication) {
        User loggedUser = (User) authentication.getPrincipal();
        User user = getUser(userId, loggedUser);

        model.addAttribute("userId", userId);
        model.addAttribute("tournaments", this.tournamentService.getAllTournaments(user));

        return "tournaments-preview";
    }

    @GetMapping("/create")
    public String redirectToCreatePage(Authentication authentication) {

        User loggedUser = (User) authentication.getPrincipal();

        return "redirect:/tournaments/" + loggedUser.getId() + "/create";
    }


    @GetMapping("/{userId}/create")
    public String getCreateTournamentPage(@PathVariable long userId, @ModelAttribute CreateTournamentBindingModel createTournamentBindingModel, Model model) {

        model.addAttribute("userId", userId);

        model.addAttribute("strategyMap", this.strategyService.getStrategyMap());

        return "tournaments-create";
    }

    @PostMapping("/{userId}/create")
    public String createTournament(@PathVariable long userId, @Valid @ModelAttribute CreateTournamentBindingModel createTournamentBindingModel, BindingResult bindingResult, Model model, Authentication authentication) {
        User loggedUser = (User) authentication.getPrincipal();

        model.addAttribute("userId", userId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("strategyMap", this.strategyService.getStrategyMap());
            return "tournaments-create";
        }

        this.tournamentService.save(createTournamentBindingModel, getUser(userId, loggedUser));

        return "redirect:/tournaments/" + userId;
    }

    @GetMapping("/{userId}/show/{tourId}")
    public String getTournamentResultPage(@PathVariable long userId, @PathVariable long tourId, @ModelAttribute SelectMatchUpResultsBindingModel selectMatchUpResultsBindingModel, Model model, Authentication authentication) {
        User loggedUser = (User) authentication.getPrincipal();

        model.addAttribute("userId", userId);

        //Throws exception if doesn't own. Exception redirects to error page
        this.tournamentService.ownsTournament(loggedUser, tourId);

        TournamentResultViewModel tournamentResultViewModel = this.tournamentService.getTournamentResultViewById(tourId);

        String data = new Gson().toJson(tournamentResultViewModel.getStrategyScoreKVPairs());

        model.addAttribute("id", tournamentResultViewModel.getId());
        model.addAttribute("data", data);
        model.addAttribute("strategies", tournamentResultViewModel.getStrategies());

        return "tournaments-show-result";
    }

    //TODO code repetition? check if redirect is possible
    @PostMapping("/{userId}/show/{tourId}")
    public String enhanceTournamentResultsPage(@PathVariable long userId, @PathVariable long tourId, @Valid @ModelAttribute SelectMatchUpResultsBindingModel selectMatchUpResultsBindingModel, BindingResult bindingResult, Model model, Authentication authentication) {

        User loggedUser = (User) authentication.getPrincipal();

        model.addAttribute("userId", userId);

        //Throws exception if doesn't own. Exception redirects to error page
        this.tournamentService.ownsTournament(loggedUser, tourId);

        TournamentResultViewModel tournamentResultViewModel = this.tournamentService.getTournamentResultViewById(tourId);

        String data = new Gson().toJson(tournamentResultViewModel.getStrategyScoreKVPairs());

        model.addAttribute("id", tournamentResultViewModel.getId());
        model.addAttribute("data", data);
        model.addAttribute("strategies", tournamentResultViewModel.getStrategies());

        if (bindingResult.hasErrors()) {
            return "tournaments-show-result";
        }

        selectMatchUpResultsBindingModel.setId(tourId);
        TournamentMatchUpResultViewModel tournamentMatchUpResultViewModel = this.tournamentService.getTournamentMatchUpResults(selectMatchUpResultsBindingModel);

        model.addAttribute("tournamentMatchUpResultViewModel", tournamentMatchUpResultViewModel);

        return "tournaments-show-result";
    }

    @GetMapping("/{userId}/edit/{tourId}")
    public String getEditTournamentPage(@PathVariable long userId, @PathVariable long tourId, Model model, Authentication authentication) {
        User loggedUser = (User) authentication.getPrincipal();

        model.addAttribute("userId", userId);

        //Throws exception if doesn't own. Exception redirects to error page
        this.tournamentService.ownsTournament(loggedUser, tourId);

        model.addAttribute("editTournamentBindingModel", this.tournamentService.getEditTournamentById(tourId));
        return "tournaments-edit";
    }

    @PostMapping("/{userId}/edit/{tourId}")
    public String editTournament(@PathVariable long userId, @PathVariable long tourId, @Valid @ModelAttribute EditTournamentBindingModel editTournamentBindingModel, BindingResult bindingResult, Model model, Authentication authentication) {

        User loggedUser = (User) authentication.getPrincipal();

        model.addAttribute("userId", userId);

        //Throws exception if doesn't own. Exception redirects to error page
        this.tournamentService.ownsTournament(loggedUser, tourId);
        editTournamentBindingModel.setId(tourId);

        if (bindingResult.hasErrors()) {
            return "tournaments-edit";
        }

        this.tournamentService.edit(editTournamentBindingModel);

        return "redirect:/tournaments/" + userId;
    }


    @GetMapping("/{userId}/delete/{tourId}")
    public String deleteTournament(@PathVariable long userId, @PathVariable long tourId, Model model, Authentication authentication) {
        User loggedUser = (User) authentication.getPrincipal();

        model.addAttribute("userId", userId);

        ///Throws exception if doesn't own. Exception redirects to error page
        this.tournamentService.ownsTournament(loggedUser, tourId);

        this.tournamentService.deleteTournamentById(tourId);

        return "redirect:/tournaments/" + userId;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String catchUserNotFoundException() {

        return "exceptions/user-not-found";
    }

    @ExceptionHandler(TournamentNotFoundException.class)
    public String catchTournamentNotFoundException() {

        return "exceptions/tournament-not-found";
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public String catchUnauthorizedAccessException() {

        return "exceptions/unauthorized-access";
    }


    private User getUser(long id, User loggedUser) {
        User user = this.userService.getUserById(id);

        if (user == null) {
                throw new UserNotFoundException();
        }

        //TODO add interceptor to validate this
        boolean isAdmin = false;
        for (Role role : loggedUser.getAuthorities()) {
            if (role.getAuthority().equals(Constants.ADMIN_ROLE)) {
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
