package com.ipdweb.areas.user.controllers;

import com.ipdweb.areas.common.utils.Constants;
import com.ipdweb.areas.user.entities.User;
import com.ipdweb.areas.user.errors.Errors;
import com.ipdweb.areas.user.exceptions.UserNotFoundException;
import com.ipdweb.areas.user.models.bindingModels.RegistrationModel;
import com.ipdweb.areas.user.models.viewModels.UserViewModel;
import com.ipdweb.areas.user.services.BasicUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {

    private BasicUserService userService;

    @Autowired
    public UserController(BasicUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterPage(@ModelAttribute RegistrationModel registrationModel) {
        return "user/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute RegistrationModel registrationModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/register";
        }

        this.userService.register(registrationModel);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String getLoginPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", Errors.INVALID_CREDENTIALS);
        }

        return "user/login";
    }

    @GetMapping("/users")
    public String getUsersPage(Model model, @PageableDefault(size = Constants.USERS_PER_PAGE) Pageable pageable) {
        Page<UserViewModel> userViewModelList = this.userService.findAll(pageable);

        model.addAttribute("users", userViewModelList);
        return "admin/admin-users";
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

    @PostMapping("/users/search")
    public String searchUsersByUsername(@RequestParam String searchWord, Model model,@PageableDefault(size = Constants.USERS_PER_PAGE) Pageable pageable) {

        Page<UserViewModel> userViewModelList = this.userService.searchUsersByUsername(searchWord,pageable);
        model.addAttribute("users",userViewModelList);

        return "admin/admin-users-partial-view";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String catchUserNotFoundException() {

        return "exceptions/user-not-found";
    }
}
