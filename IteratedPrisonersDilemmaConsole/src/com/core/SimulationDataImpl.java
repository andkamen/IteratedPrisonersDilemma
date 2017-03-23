package com.core;

import com.core.contracts.SimulationData;
import com.exceptions.DuplicateEntryInStructureException;
import com.exceptions.KeyNotFoundException;
import com.simulation.contracts.Generation;
import com.simulation.contracts.Simulation;
import com.utilities.Messages;

import java.util.HashMap;

public class SimulationDataImpl implements SimulationData {

    private HashMap<String, Simulation> simulations;
    private HashMap<String, Generation> tournaments;

    public SimulationDataImpl() {
        this.simulations = new HashMap<>();
        this.tournaments = new HashMap<>();
    }

    public Simulation getSimulation(String name) {
        if (!simulations.containsKey(name)) {
            throw new KeyNotFoundException();
        }

        return this.simulations.get(name);
    }

    public Generation getTournament(String name) {
        if (!tournaments.containsKey(name)) {
            throw new KeyNotFoundException();
        }

        return this.tournaments.get(name);
    }

    public String addSimulation(Simulation simulation) {
        if (this.simulations.containsKey(simulation.getName())) {
            throw new DuplicateEntryInStructureException(simulation.getName(), "simulation");
        }

        this.simulations.put(simulation.getName(), simulation);
        return String.format(Messages.SUCCESSFULLY_REGISTERED_ITEM, simulation.getName(), "simulation");
    }

    public String removeSimulation(String name) {
        if (!this.simulations.containsKey(name)) {
            throw new KeyNotFoundException();
        }

        this.simulations.remove(name);
        return String.format(Messages.SUCCESSFULLY_REMOVED_ITEM, name, "simulation");
    }

    public String addTournament(Generation tournament) {
        if (this.tournaments.containsKey(tournament.getName())) {
            throw new DuplicateEntryInStructureException(tournament.getName(), "tournament");
        }

        this.tournaments.put(tournament.getName(), tournament);
        return String.format(Messages.SUCCESSFULLY_REGISTERED_ITEM, tournament.getName(), "tournament");
    }

    public String removeTournament(String name) {
        if (!this.tournaments.containsKey(name)) {
            throw new KeyNotFoundException();
        }

        this.tournaments.remove(name);
        return String.format(Messages.SUCCESSFULLY_REMOVED_ITEM, name, "tournament");
    }


}
