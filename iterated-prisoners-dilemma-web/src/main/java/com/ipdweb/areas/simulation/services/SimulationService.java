package com.ipdweb.areas.simulation.services;

import com.ipdweb.areas.simulation.entities.Simulation;
import com.ipdweb.areas.simulation.models.bindingModels.CreateSimulationBindingModel;
import com.ipdweb.areas.simulation.models.viewModels.SimulationPreviewViewModel;
import com.ipdweb.areas.user.entities.User;

import java.util.Set;

public interface SimulationService {

    void save(CreateSimulationBindingModel createSimulationBindingModel, User principal);

    Simulation getSimulationById(Long id);

    void resetSimulation(Long id);

    void deleteSimulationById(Long id);

    Set<SimulationPreviewViewModel> getAllSimulations(User user);
}
