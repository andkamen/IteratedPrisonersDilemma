package com.ipdweb.areas.strategy.factories;

import com.ipdweb.areas.strategy.entities.StrategyImpl;
import com.ipdweb.exceptions.InvalidStrategyNameException;
import com.ipdweb.areas.common.utils.Constants;

import java.lang.reflect.Constructor;
import java.util.List;

public class StrategyFactoryImpl implements StrategyFactory {

    public StrategyFactoryImpl() {
    }

    @Override
    public StrategyImpl buildStrategy(String strategyType) {
        StrategyImpl newStrategy = null;
        try {
            String strategyFullPath = Constants.STRATEGY_PACKAGE + strategyType + Constants.STRATEGY_SUFFIX;
            Class strategyClass = Class.forName(strategyFullPath);
            Constructor ctor = strategyClass.getConstructor();

            newStrategy = (StrategyImpl) ctor.newInstance();
        } catch (Exception e) {
            throw new InvalidStrategyNameException(strategyType);
        }
        return newStrategy;
    }


    //TODO wtf hacks
    @Override
    public StrategyImpl hackStrategy(String strategyType, List<StrategyImpl> strategies) {
        StrategyImpl strategy = null;

        for (StrategyImpl strat : strategies) {
            if (strat.getName().equals(strategyType)) {
                strategy = strat;
                break;
            }
        }

        return strategy;
    }
}
