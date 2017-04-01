package com.ipdweb.areas.simulation.services;

import com.ipdweb.areas.simulation.entities.Simulation;
import com.ipdweb.areas.simulation.models.bindingModels.CreateSimulationBindingModel;
import com.ipdweb.areas.simulation.models.bindingModels.EditSimulationBindingModel;
import com.ipdweb.areas.simulation.models.viewModels.SimulationPreviewViewModel;
import com.ipdweb.areas.user.entities.User;

import java.util.Set;

public interface SimulationService {

    void save(CreateSimulationBindingModel createSimulationBindingModel, User principal);

    void edit(EditSimulationBindingModel editSimulationBindingModel);

    Simulation getSimulationById(Long id);

    EditSimulationBindingModel getEditSimulationById(Long id);

    void resetSimulation(Long id);

    void deleteSimulationById(Long id);

    Set<SimulationPreviewViewModel> getAllSimulations(User user);

    boolean ownsSimulation(User user, Long simId);
}
