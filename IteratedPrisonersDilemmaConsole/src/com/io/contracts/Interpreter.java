package com.io.contracts;

import com.io.commands.contracts.Command;

public interface Interpreter {

    Command dispatchCommand(String commandName, String[] arguments);
}
