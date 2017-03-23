package com.io.commands;

import com.io.commands.contracts.Command;
import com.core.contracts.SimulationData;

import java.io.IOException;

public abstract class BaseCommand implements Command {

    private SimulationData data;
    private String[] arguments;

    public BaseCommand(SimulationData data,String[] arguments) {
        this.setData(data);
        this.setArguments(arguments);
    }

    protected SimulationData getData() {
        return this.data;
    }

    protected void setData(SimulationData data) {
        this.data = data;
    }

    protected String[] getArguments() {
        return arguments;
    }

    protected void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    @Override
    public abstract String execute() throws IOException;
}
