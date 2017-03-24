package com.ipdweb.servicesImpl;


import com.ipdweb.entities.strategy.StrategyImpl;
import com.ipdweb.repositories.StrategyRepository;
import com.ipdweb.services.StrategyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
