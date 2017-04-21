package com.ipdweb.areas.tournament.interceptors;

import com.ipdweb.areas.user.services.BasicUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TournamentInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private BasicUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String name =  request.getUserPrincipal().getName();
        UserDetails userDetails = this.userService.loadUserByUsername(name);

        response.sendRedirect("/tournaments/" + userDetails);

        return false;
    }
}
