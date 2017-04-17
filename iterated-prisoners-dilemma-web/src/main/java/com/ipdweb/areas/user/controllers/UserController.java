package com.ipdweb.areas.user.controllers;

import com.ipdweb.areas.tournament.services.TournamentService;
import com.ipdweb.areas.user.entities.BasicUser;
import com.ipdweb.areas.user.entities.FacebookUser;
import com.ipdweb.areas.user.entities.User;
import com.ipdweb.areas.user.errors.Errors;
import com.ipdweb.areas.user.exceptions.UserNotFoundException;
import com.ipdweb.areas.user.models.bindingModels.RegistrationModel;
import com.ipdweb.areas.user.models.viewModels.UserViewModel;
import com.ipdweb.areas.user.services.BasicUserService;
import com.ipdweb.areas.user.services.FacebookUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private BasicUserService basicUserService;

    @Autowired
    private FacebookUserService facebookUserService;

    @Autowired
    private TournamentService tournamentService;

    @GetMapping("/register")
    public String getRegisterPage(@ModelAttribute RegistrationModel registrationModel) {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute RegistrationModel registrationModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        this.basicUserService.register(registrationModel);

        return "redirect:/";
    }

    //TODO precise error message? disabled/invalid login etc
    @GetMapping("/login")
    public String getLoginPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", Errors.INVALID_CREDENTIALS);
        }

        return "login";
    }

    @GetMapping("/users")
    public String getUsersPage(Model model, Authentication authentication) {
        User loggedUser = (User) authentication.getPrincipal();
        List<UserViewModel> userViewModelList = this.basicUserService.findAll();

        model.addAttribute("loggedUserId", loggedUser.getId());
        model.addAttribute("users", userViewModelList);
        return "admin-users";
    }

    @GetMapping("/users/delete/{userId}")
    public String deleteUser(@PathVariable long userId, Model model) {

        BasicUser basicUser = this.basicUserService.getUserById(userId);
        if (basicUser != null) {
            this.basicUserService.deleteUserById(userId);
        }

        FacebookUser facebookUser = this.facebookUserService.getUserById(userId);
        if (facebookUser != null) {
            this.facebookUserService.deleteUserById(userId);
        }

        return "redirect:/users";
    }

    @PostMapping("/users/disable/{userId}")
    public String disableUser(@PathVariable long userId, Model model) {
        BasicUser basicUser = this.basicUserService.getUserById(userId);
        if (basicUser != null) {
            this.basicUserService.disableUser(basicUser);
        }

        FacebookUser facebookUser = this.facebookUserService.getUserById(userId);
        if (facebookUser != null) {
            this.facebookUserService.disableUser(facebookUser);
        }

        return "redirect:/users";
    }

    @PostMapping("/users/enable/{userId}")
    public String enableUser(@PathVariable long userId, Model model) {
        BasicUser basicUser = this.basicUserService.getUserById(userId);
        if (basicUser != null) {
            this.basicUserService.enableUser(basicUser);
        }

        FacebookUser facebookUser = this.facebookUserService.getUserById(userId);
        if (facebookUser != null) {
            this.facebookUserService.enableUser(facebookUser);
        }
        return "redirect:/users";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String catchUserNotFoundException() {

        return "exceptions/user-not-found";
    }


    //TODO this error is not even caught when thrown
    @ExceptionHandler(DisabledException.class)
    public String catchDisabledException(Model model) {
        model.addAttribute("error", Errors.ACCOUNT_DISABLED);

        return "login";
    }
}
