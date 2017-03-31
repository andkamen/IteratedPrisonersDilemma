package com.ipdweb.areas.strategy.servicesImpl;


import com.ipdweb.areas.strategy.entities.StrategyImpl;
import com.ipdweb.areas.strategy.models.viewModels.StrategyMapViewModel;
import com.ipdweb.areas.strategy.models.viewModels.StrategyViewModel;
import com.ipdweb.areas.strategy.repositories.StrategyRepository;
import com.ipdweb.areas.strategy.services.StrategyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class StrategyServiceImpl implements StrategyService {

    @Autowired
    private StrategyRepository strategyRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public StrategyImpl getStrategyById(Long id) {
        return this.strategyRepository.getById(id);
    }

    @Override
    public StrategyImpl getStrategyByName(String name) {
        return this.strategyRepository.getByName(name);
    }

    @Override
    public Set<StrategyViewModel> getAllStrategies() {
        List<StrategyImpl> strategyImpls = this.strategyRepository.getAllStrategies();
        Set<StrategyViewModel> strategies = new LinkedHashSet<>();

        for (StrategyImpl strategyImpl : strategyImpls) {
            StrategyViewModel strategy = this.modelMapper.map(strategyImpl, StrategyViewModel.class);
            strategies.add(strategy);
        }

        return strategies;
    }

    @Override
    public List<StrategyImpl> getAllStrategyImpls() {
        return this.strategyRepository.getAllStrategies();
    }

    @Override
    public StrategyMapViewModel getStrategyMap() {
        List<StrategyImpl> strategyImpls = this.strategyRepository.getAllStrategies();
        StrategyMapViewModel strategyMap = new StrategyMapViewModel();

        for (StrategyImpl strategyImpl : strategyImpls) {
            strategyMap.getStrategies().put(strategyImpl.getName(), 0);
        }

        return strategyMap;
    }
}
