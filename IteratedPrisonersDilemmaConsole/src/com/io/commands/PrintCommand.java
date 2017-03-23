package com.io.commands;

import com.core.contracts.SimulationData;
import com.exceptions.InvalidInputException;
import com.io.contracts.FileIO;
import com.simulation.contracts.Generation;
import com.simulation.contracts.Simulation;
import com.utilities.Constants;
import com.utilities.Messages;

import java.io.IOException;

public class PrintCommand extends BaseCommand {

    private FileIO fileIO;

    public PrintCommand(SimulationData data, String[] arguments, FileIO fileIO) {
        super(data, arguments);
        this.fileIO = fileIO;
    }

    @Override
    public String execute() throws IOException {
        String[] arguments = this.getArguments();
        StringBuilder consoleOutput = new StringBuilder();
        String simulationType = arguments[0];
        String simulationName = arguments[1];
        boolean printMode = false;
        if (arguments.length > 2 && arguments[2].toLowerCase().equals("verbose")) {
            printMode = true;
        }

        switch (simulationType.toLowerCase()) {
            case Constants.SIMULATION_TYPE_SIMULATION:
                consoleOutput.append(printSimulationData(this.getData().getSimulation(simulationName), printMode))
                        .append(System.lineSeparator());
                break;
            case Constants.SIMULATION_TYPE_TOURNAMENT:
                consoleOutput.append(printTournamentData(this.getData().getTournament(simulationName), printMode))
                        .append(System.lineSeparator());
                break;
            default:
                throw new InvalidInputException(simulationType);
        }

        return consoleOutput.toString();
    }


    private String printTournamentData(Generation tournament, boolean isVerboseMode) {
        String fileName = tournament.getName() + "TournamentResults.txt";
        String filePath = Constants.OUTPUT_FOLDER_PATH + fileName;

        String info = String.format("%s TOURNAMENT RESULTS %s", tournament.getName().toUpperCase(), System.lineSeparator());
        try {
            this.fileIO.write(info + tournament.print(isVerboseMode), filePath, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.format(Messages.SUCCESSFULLY_WROTE_RESULTS_TO_FILE, fileName);
    }

    private String printSimulationData(Simulation simulation, boolean isVerboseMode) {
        String fileName = simulation.getName() + "SimulationResults.txt";
        String filePath = Constants.OUTPUT_FOLDER_PATH + fileName;

        String info = String.format("%s SIMULATION RESULTS %s", simulation.getName().toUpperCase(), System.lineSeparator());
        try {
            this.fileIO.write(info, filePath, false);
            for (int i = 0; i < simulation.getGenerationSize() - 1; i++) {
                this.fileIO.write(simulation.print(i, isVerboseMode),filePath,true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.format(Messages.SUCCESSFULLY_WROTE_RESULTS_TO_FILE, fileName);
    }
}
