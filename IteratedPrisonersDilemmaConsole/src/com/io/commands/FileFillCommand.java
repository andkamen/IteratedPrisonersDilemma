package com.io.commands;

import com.core.contracts.SimulationData;
import com.core.contracts.StrategyFactory;
import com.exceptions.InvalidInputException;
import com.simulation.contracts.Strategy;
import com.utilities.Constants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileFillCommand extends BaseCommand {
    private StrategyFactory strategyFactory;

    public FileFillCommand(SimulationData data, StrategyFactory strategyFactory, String[] arguments) {
        super(data, arguments);
        this.strategyFactory = strategyFactory;
    }

    @Override
    public String execute() throws IOException {
        String[] arguments = this.getArguments();
        StringBuilder consoleOutput = new StringBuilder();
        String simulationType = arguments[0];
        String simulationName = arguments[1];
        String fileName = arguments[3] + Constants.TEXT_FILE_EXTENSION;

        String path = Constants.INPUT_FOLDER_PATH + fileName;
        List<String> lines = Files.readAllLines(Paths.get(path));

        for (String line : lines) {
            String[] args = line.split(Constants.SPACE_SPLIT_DELIMITER);
            String strategyName = args[0];
            int strategyCount = 1;
            if (args.length > 1) {
                strategyCount = Integer.parseInt(args[1]);
                if(strategyCount<=0){
                    throw new IndexOutOfBoundsException(String.format("%s count cannot be less than 1",strategyName));
                }
            }

            //
            // TODO code duplication. Abstract fill command?
            /*
            moving the code to a function doesn't solve the problem. Still needs to be defined in the command.
            Moving it to base command means it has to know about factories and every other command will have access to it too.
             */

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
