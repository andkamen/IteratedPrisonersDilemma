package com.ipdweb.areas.user.controllers;

import com.ipdweb.areas.user.entities.User;
import com.ipdweb.areas.user.errors.Errors;
import com.ipdweb.areas.user.exceptions.AccountDisabledException;
import com.ipdweb.areas.user.exceptions.UserNotFoundException;
import com.ipdweb.areas.user.models.bindingModels.RegistrationModel;
import com.ipdweb.areas.user.models.viewModels.UserViewModel;
import com.ipdweb.areas.user.services.BasicUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private BasicUserService userService;

    @Autowired
    public UserController(BasicUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterPage(@ModelAttribute RegistrationModel registrationModel) {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute RegistrationModel registrationModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        this.userService.register(registrationModel);

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
        List<UserViewModel> userViewModelList = this.userService.findAll();

        model.addAttribute("loggedUserId", loggedUser.getId());
        model.addAttribute("users", userViewModelList);
        return "admin-users";
    }

    @GetMapping("/users/delete/{userId}")
    public String deleteUser(@PathVariable long userId) {

        User user = this.userService.getUserById(userId);
        if (user != null) {
            this.userService.deleteUserById(userId);
        }

        return "redirect:/users";
    }

    @PostMapping("/users/changeAccess/{enabled}/{userId}")
    public String changeUserAccountAccess(@PathVariable boolean enabled, @PathVariable long userId) {
        User user = this.userService.getUserById(userId);
        if (user != null) {
            this.userService.changeAccountAccess(user, enabled);
        }

        return "redirect:/users";
    }


    @ExceptionHandler(UserNotFoundException.class)
    public String catchUserNotFoundException() {

        return "exceptions/user-not-found";
    }

    //TODO this error because Spring. read documentation
    //Doesnt work with Disabled access exception either
    @ExceptionHandler(AccountDisabledException.class)
    public String catchDisabledException(Model model) {
        model.addAttribute("error", Errors.ACCOUNT_DISABLED);

        return "login";
    }
}
