package com.io.commands;

import com.core.contracts.SimulationData;
import com.core.contracts.StrategyFactory;
import com.exceptions.InvalidInputException;
import com.simulation.models.GenerationImpl;
import com.simulation.models.SimulationImpl;
import com.utilities.Constants;

public class CreateCommand extends BaseCommand {

    private StrategyFactory strategyFactory;

    public CreateCommand(SimulationData data, StrategyFactory strategyFactory, String[] arguments) {
        super(data,arguments);
        this.strategyFactory = strategyFactory;
    }

    @Override
    public String execute() {
        String[] arguments = this.getArguments();
        String simulationType = arguments[0];
        String name = arguments[1];

        
        switch (simulationType.toLowerCase()) {
            case Constants.SIMULATION_TYPE_SIMULATION:
                return this.getData().addSimulation(new SimulationImpl(name, this.strategyFactory));
            case Constants.SIMULATION_TYPE_TOURNAMENT:
                return this.getData().addTournament(new GenerationImpl(name));
            default:
                throw new InvalidInputException(simulationType.toLowerCase());
        }
    }
}
