package com.ipdweb.areas.simulation.services;

import com.ipdweb.areas.simulation.models.bindingModels.CreateSimulationBindingModel;
import com.ipdweb.areas.simulation.models.bindingModels.EditSimulationBindingModel;
import com.ipdweb.areas.simulation.models.viewModels.SimulationPreviewViewModel;
import com.ipdweb.areas.simulation.models.viewModels.SimulationResultViewModel;
import com.ipdweb.areas.user.entities.User;

import java.util.Set;

public interface SimulationService {

    void save(CreateSimulationBindingModel createSimulationBindingModel, User principal);

    void edit(EditSimulationBindingModel editSimulationBindingModel);

    EditSimulationBindingModel getEditSimulationById(Long id);

    SimulationResultViewModel getSimulationResultViewById(Long id);

    Set<SimulationPreviewViewModel> getAllSimulations(User user);

    void deleteSimulationById(Long id);

    void resetSimulation(Long id);

    boolean ownsSimulation(User user, Long simId);
}
