package com.ipdweb.areas.strategy.controllers;

import com.ipdweb.areas.strategy.models.viewModels.StrategyViewModel;
import com.ipdweb.areas.strategy.services.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/strategies")
public class StrategyController {


    @Autowired
    private StrategyService strategyService;

    @ModelAttribute("strategies")
    public Set<StrategyViewModel> getAllStrategies() {
        Set<StrategyViewModel> strategies = this.strategyService.getAllStrategies();
        return strategies;
    }


    @GetMapping("")
    public String getStrategiesPage() {
        return "strategies";
    }

}
