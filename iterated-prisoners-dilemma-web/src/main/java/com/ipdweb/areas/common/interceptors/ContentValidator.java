package com.ipdweb.areas.common.interceptors;

import com.ipdweb.areas.common.exceptions.UnauthorizedAccessException;
import com.ipdweb.areas.common.utils.Constants;
import com.ipdweb.areas.user.entities.Role;
import com.ipdweb.areas.user.entities.User;
import com.ipdweb.areas.user.services.BasicUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ContentValidator extends HandlerInterceptorAdapter {

    @Autowired
    private BasicUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getRequestURI().equals(Constants.VIEW_SIMULATION_URI) ||
                request.getRequestURI().equals(Constants.CREATE_SIMULATION_URI) ||
                request.getRequestURI().equals(Constants.VIEW_TOURNAMENTS_URI) ||
                request.getRequestURI().equals(Constants.CREATE_TOURNAMENTS_URI)) {
            return true;
        }

        String[] uri = request.getRequestURI().trim().split("/");


        String loggedUserName = request.getUserPrincipal().getName();
        User loggedUser = this.userService.getUserByUsername(loggedUserName);
        User user = this.userService.getUserById(Long.parseLong(uri[Constants.USER_ID_POSITION]));

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

        return true;
    }
}
