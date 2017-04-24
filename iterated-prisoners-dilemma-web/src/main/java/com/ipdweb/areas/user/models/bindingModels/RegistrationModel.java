package com.ipdweb.areas.user.models.bindingModels;


import com.ipdweb.areas.user.customValidations.IsPasswordsMatching;
import com.ipdweb.areas.user.customValidations.IsUsernameUnique;

import javax.validation.constraints.Size;


@IsPasswordsMatching
public class RegistrationModel {

    @IsUsernameUnique
    @Size(min = 5, message = "Username too short")
    private String username;

    @Size(min = 4, message = "Password too short")
    private String password;

    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
