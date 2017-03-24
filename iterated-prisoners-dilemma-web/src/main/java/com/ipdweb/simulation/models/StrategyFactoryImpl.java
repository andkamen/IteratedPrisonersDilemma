package com.ipdweb.simulation.models;

import com.ipdweb.exceptions.InvalidStrategyNameException;
import com.ipdweb.entities.strategy.interfaces.Strategy;
import com.ipdweb.simulation.interfaces.StrategyFactory;
import com.ipdweb.utils.Constants;

import java.lang.reflect.Constructor;

public class StrategyFactoryImpl implements StrategyFactory {

    public StrategyFactoryImpl() {
    }

    @Override
    public Strategy buildStrategy(String strategyType) {
        Strategy newStrategy = null;
        try {
            String strategyFullPath = Constants.STRATEGY_PACKAGE + strategyType + Constants.STRATEGY_SUFFIX;
            Class strategyClass = Class.forName(strategyFullPath);
            Constructor ctor = strategyClass.getConstructor();

            newStrategy = (Strategy) ctor.newInstance();
        } catch (Exception e) {
            throw new InvalidStrategyNameException(strategyType);
        }
        return newStrategy;
    }
}
