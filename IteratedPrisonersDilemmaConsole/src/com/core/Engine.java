package com.core;

import com.core.contracts.EngineInterface;
import com.core.contracts.SimulationData;
import com.core.contracts.StrategyFactory;
import com.io.CommandInterpreter;
import com.io.ConsoleIOImpl;
import com.io.FileIOImpl;
import com.io.commands.contracts.Command;
import com.io.contracts.ConsoleIO;
import com.io.contracts.FileIO;
import com.io.contracts.Interpreter;
import com.utilities.Constants;

import java.util.Arrays;

public class Engine implements EngineInterface {

    private ConsoleIO consoleIO;
    private FileIO fileIO;
    private StrategyFactory strategyFactory;
    private SimulationData data;
    private Interpreter commandInterpreter;

    private Boolean isRunning;


    public Engine() {
        this.consoleIO = new ConsoleIOImpl();
        this.fileIO = new FileIOImpl();
        this.strategyFactory = new StrategyFactoryImpl();
        this.data = new SimulationDataImpl();
        this.commandInterpreter = new CommandInterpreter(this.data, this.strategyFactory,this.fileIO);
    }

    @Override
    public void run() {
        this.isRunning = true;

        while (this.isRunning) {
            String inputLine = this.consoleIO.readLine();

            this.processInput(inputLine);
        }
    }

    private void processInput(String input) {

        String[] splitArgs = input.split(Constants.SPACE_SPLIT_DELIMITER);

        String commandName = splitArgs[0];

        if (commandName.toLowerCase().equals(Constants.INPUT_TERMINATING_COMMAND)) {
            this.isRunning = false;
            return;
        }

        String[] filteredArgs = null;
        if (splitArgs.length > 1) {
            filteredArgs = Arrays.copyOfRange(splitArgs, 1, splitArgs.length);
        }
        try {
            Command command = commandInterpreter.dispatchCommand(commandName, filteredArgs);

            String commandResult = command.execute();
            this.consoleIO.writeLine(commandResult);
        } catch (Exception e) {
            this.consoleIO.writeLine(e.getMessage());
        }
    }
}
