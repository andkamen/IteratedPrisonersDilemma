package com.ipdweb.areas.strategy.services;

import com.ipdweb.areas.strategy.entities.StrategyImpl;
import com.ipdweb.areas.strategy.models.viewModels.StrategyMapViewModel;
import com.ipdweb.areas.strategy.models.viewModels.StrategyViewModel;

import java.util.List;
import java.util.Set;

public interface StrategyService {

    Set<StrategyViewModel> getAllStrategies();

    List<StrategyImpl> getAllStrategyImpls();

    StrategyMapViewModel getStrategyMap();
}
