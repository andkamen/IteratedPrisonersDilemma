package com.ipdweb.services;

import com.ipdweb.entities.strategy.StrategyImpl;

public interface StrategyService {

    StrategyImpl getStrategyById(Long id);

    StrategyImpl getStrategyByName(String name);
}
