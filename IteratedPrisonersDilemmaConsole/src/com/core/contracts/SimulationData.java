package com.core.contracts;

import com.simulation.contracts.Generation;
import com.simulation.contracts.Simulation;

public interface SimulationData {

    Simulation getSimulation(String name);

    Generation getTournament(String name);

    String addSimulation(Simulation simulation);

    String removeSimulation(String name);

    String addTournament(Generation tournament);

    String removeTournament(String name);
}
