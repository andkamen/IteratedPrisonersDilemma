package com.ipdweb.areas.simulation.services;

import com.ipdweb.areas.simulation.entities.Simulation;

public interface SimulationService {

    void save(Simulation simulation);

    Simulation getSimulationById(Long id);

    void resetSimulation(Long id);

    void deleteSimulationById(Long id);
}
