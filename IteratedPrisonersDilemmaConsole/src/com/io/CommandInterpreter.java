package com.io;

import com.exceptions.InvalidInputException;
import com.exceptions.MissingCommandArgumentsException;
import com.io.commands.*;
import com.io.commands.contracts.Command;
import com.io.contracts.FileIO;
import com.io.contracts.Interpreter;
import com.core.contracts.SimulationData;
import com.core.contracts.StrategyFactory;
import com.utilities.Constants;

public class CommandInterpreter implements Interpreter {

    private SimulationData data;
    private StrategyFactory strategyFactory;
    private FileIO fileIO;

    public CommandInterpreter(SimulationData data, StrategyFactory strategyFactory, FileIO fileIO) {
        this.data = data;
        this.strategyFactory = strategyFactory;
        this.fileIO = fileIO;
    }

    @Override
    public Command dispatchCommand(String commandName, String[] arguments) {
        Command command = null;

        //TODO check arguments length. ! number req should throw
        switch (commandName.toLowerCase()) {
            case "create":
                command = new CreateCommand(this.data, this.strategyFactory, arguments);
                break;
            case "fill":
                if (arguments.length < 4) {
                    throw new MissingCommandArgumentsException();
                }
                String mode = arguments[2];
                switch (mode.toLowerCase()) {
                    case Constants.FILL_MODE_MANUAL:
                        command = new ManualFillCommand(this.data, this.strategyFactory, arguments);
                        break;
                    case Constants.FILL_MODE_FILE:
                        command = new FileFillCommand(this.data, this.strategyFactory, arguments);
                        break;
                    default:
                        throw new InvalidInputException(mode.toLowerCase());
                }
                break;
            case "run":
                command = new RunCommand(this.data, arguments);
                break;
            case "print":
                command = new PrintCommand(this.data, arguments, this.fileIO);
                break;
            case "help":
                command = new HelpCommand(arguments);
                break;
            default:
                throw new InvalidInputException(commandName.toLowerCase());
        }

        return command;
    }
}
