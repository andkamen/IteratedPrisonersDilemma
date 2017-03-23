package com.io.commands;

import com.core.contracts.SimulationData;
import com.core.contracts.StrategyFactory;
import com.exceptions.InvalidInputException;
import com.simulation.contracts.Strategy;
import com.utilities.Constants;

/**
 * Fills a tournament or simulation with strategies give via console input
 */
public class ManualFillCommand extends BaseCommand {


    private StrategyFactory strategyFactory;

    public ManualFillCommand(SimulationData data, StrategyFactory strategyFactory, String[] arguments) {
        super(data, arguments);
        this.strategyFactory = strategyFactory;
    }

    @Override
    public String execute() {
        String[] arguments = this.getArguments();
        StringBuilder consoleOutput = new StringBuilder();
        String simulationType = arguments[0];
        String simulationName = arguments[1];

        String[] strategyInfo = arguments[3].split(Constants.PIPE_SPLIT_DELIMITER);

        for (String info : strategyInfo) {
            String[] args = info.split(Constants.UNDERSCORE_SPLIT_DELIMITER);
            String strategyName = args[0];
            int strategyCount = 1;
            if (args.length > 1) {
                strategyCount = Integer.parseInt(args[1]);
                if(strategyCount<=0){
                    throw new IndexOutOfBoundsException(String.format("%s count cannot be less than 1",strategyName));
                }
            }

            for (int i = 0; i < strategyCount; i++) {
                Strategy newStrategy = this.strategyFactory.buildStrategy(strategyName);

                switch (simulationType.toLowerCase()) {
                    case Constants.SIMULATION_TYPE_SIMULATION:
                        consoleOutput.append(this.getData().getSimulation(simulationName).addStrategy(newStrategy)).append(System.lineSeparator());
                        break;
                    case Constants.SIMULATION_TYPE_TOURNAMENT:
                        consoleOutput.append(this.getData().getTournament(simulationName).addStrategy(newStrategy)).append(System.lineSeparator());
                        break;
                    default:
                        throw new InvalidInputException(simulationType);
                }
            }
        }
        consoleOutput.setLength(consoleOutput.length() - 1);
        return consoleOutput.toString();
    }
}
