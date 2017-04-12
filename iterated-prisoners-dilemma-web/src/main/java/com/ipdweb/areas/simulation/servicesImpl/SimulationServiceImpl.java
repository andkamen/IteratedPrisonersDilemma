package com.ipdweb.areas.simulation.servicesImpl;


import com.ipdweb.areas.simulation.entities.Generation;
import com.ipdweb.areas.simulation.entities.Simulation;
import com.ipdweb.areas.simulation.exceptions.SimulationNotFoundException;
import com.ipdweb.areas.simulation.exceptions.UnauthorizedSimulationAccessException;
import com.ipdweb.areas.simulation.models.bindingModels.CreateSimulationBindingModel;
import com.ipdweb.areas.simulation.models.bindingModels.EditSimulationBindingModel;
import com.ipdweb.areas.simulation.models.bindingModels.RunMoreGenerationsBindingModel;
import com.ipdweb.areas.simulation.models.viewModels.SimulationPreviewViewModel;
import com.ipdweb.areas.simulation.models.viewModels.SimulationResultViewModel;
import com.ipdweb.areas.simulation.repositories.SimulationRepository;
import com.ipdweb.areas.simulation.services.GenerationService;
import com.ipdweb.areas.simulation.services.SimulationService;
import com.ipdweb.areas.strategy.entities.StrategyImpl;
import com.ipdweb.areas.strategy.factories.StrategyFactory;
import com.ipdweb.areas.strategy.factories.StrategyFactoryImpl;
import com.ipdweb.areas.strategy.models.viewModels.StrategyMapViewModel;
import com.ipdweb.areas.strategy.services.StrategyService;
import com.ipdweb.areas.user.entities.Role;
import com.ipdweb.areas.user.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SimulationServiceImpl implements SimulationService {

    @Autowired
    private SimulationRepository simulationRepository;

    @Autowired
    private GenerationService generationService;

    @Autowired
    private StrategyService strategyService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public void save(CreateSimulationBindingModel createSimulationBindingModel, User user) {
        List<StrategyImpl> strategies = this.strategyService.getAllStrategyImpls();
        StrategyFactory strategyFactory = new StrategyFactoryImpl();

        Simulation simulation = new Simulation(createSimulationBindingModel.getName());
        simulation.setUser(user);

        for (Map.Entry<String, Integer> entry : createSimulationBindingModel.getStrategies().entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                StrategyImpl strategy = strategyFactory.hackStrategy(entry.getKey(), strategies);
                simulation.addStrategy(strategy);
            }
        }

        simulation.run(createSimulationBindingModel.getGenerationCount());

        this.simulationRepository.save(simulation);
    }

    @Override
    public void edit(EditSimulationBindingModel editSimulationBindingModel) {
        List<StrategyImpl> strategies = this.strategyService.getAllStrategyImpls();
        StrategyFactory strategyFactory = new StrategyFactoryImpl();

        Simulation simulation = this.simulationRepository.getSimulationById(editSimulationBindingModel.getId());
        Generation firstGeneration = simulation.getGenerations().get(0);

        simulation.getGenerations().clear();

        firstGeneration.getStrategies().clear();
        firstGeneration.getGenerationMatchUpResults().clear();

        simulation.setName(editSimulationBindingModel.getName());
        simulation.getGenerations().add(firstGeneration);

        for (Map.Entry<String, Integer> entry : editSimulationBindingModel.getStrategies().entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                StrategyImpl strategy = strategyFactory.hackStrategy(entry.getKey(), strategies);
                firstGeneration.addStrategy(strategy);
            }
        }

        simulation.run(editSimulationBindingModel.getGenerationCount());

        this.simulationRepository.save(simulation);
    }

    @Override
    public EditSimulationBindingModel getEditSimulationById(Long id) {
        //get currently registered strategies + other info
        Simulation simulationDB = this.simulationRepository.getSimulationById(id);
        if (simulationDB == null) {
            throw new SimulationNotFoundException();
        }

        //get a map with all basic strategies and a count of 0
        StrategyMapViewModel strategyMap = this.strategyService.getStrategyMap();
        EditSimulationBindingModel editSimulation = this.modelMapper.map(simulationDB, EditSimulationBindingModel.class);

        Map<String, Integer> strategies = strategyMap.getStrategies();

        //make the strategy map represent total number of strats registered in the tournament
        for (StrategyImpl strategy : simulationDB.getGenerations().get(0).getStrategies()) {
            if (strategies.containsKey(strategy.getName())) {
                int count = strategies.get(strategy.getName()) + 1;
                strategies.put(strategy.getName(), count);
            }
        }

        editSimulation.setStrategies(strategies);
        editSimulation.setGenerationCount(simulationDB.getGenerationCount() - 1);


        return editSimulation;
    }

    @Override
    public SimulationResultViewModel getSimulationResultViewById(Long id) {
        Simulation simulation = getFullSimulationById(id);

        SimulationResultViewModel simulationResultViewModel = this.modelMapper.map(simulation, SimulationResultViewModel.class);
        simulationResultViewModel.setGenerationCount(simulation.getGenerationCount() - 1);

        List<String> strategyNames = new ArrayList<>();
        Map<String, Integer> strategyIndex = new HashMap<>();

        strategyNames.add("Generation");
        for (String strategyName : simulation.getGenerations().get(0).getStrategyScores().keySet()) {
            strategyIndex.put(strategyName, strategyNames.size());
            strategyNames.add(strategyName);
        }

        int[][] countMatrix = new int[simulation.getGenerationCount() - 1][strategyNames.size()];

        for (int i = 0; i < simulation.getGenerationCount() - 1; i++) {
            countMatrix[i][0] = i;
            for (Map.Entry<String, Integer> entry : simulation.getGenerations().get(i).getStrategyCount().entrySet()) {
                int index = strategyIndex.get(entry.getKey());
                countMatrix[i][index] = entry.getValue();
            }
        }

        simulationResultViewModel.setStrategyNames(strategyNames);
        simulationResultViewModel.setStrategyCounts(countMatrix);

        return simulationResultViewModel;
    }

    @Override
    public Set<SimulationPreviewViewModel> getAllSimulations(User user) {

        Set<Simulation> allSimulations = this.simulationRepository.getAllSimulations(user);
        Set<SimulationPreviewViewModel> simulations = new LinkedHashSet<>();

        //reconstruct strategy Count map. (Its transient so cannot be retrieved from DB)
        for (Simulation sim : allSimulations) {
            SimulationPreviewViewModel simulationPreviewModel = this.modelMapper.map(sim, SimulationPreviewViewModel.class);

            Map<String, Integer> strategyCount = new LinkedHashMap<>();
            for (StrategyImpl strategy : sim.getGenerations().get(0).getStrategies()) {
                if (!strategyCount.containsKey(strategy.getName())) {
                    strategyCount.put(strategy.getName(), 0);
                }
                strategyCount.put(strategy.getName(), strategyCount.get(strategy.getName()) + 1);
            }
            simulationPreviewModel.setStrategyCount(strategyCount);

            simulationPreviewModel.setGenerationCount(sim.getGenerationCount() - 1);
            simulations.add(simulationPreviewModel);
        }

        return simulations;
    }

    @Override
    public void runExtraGenerations(RunMoreGenerationsBindingModel runMoreGenerationsBindingModel) {
        Simulation simulation = this.getFullSimulationById(runMoreGenerationsBindingModel.getId());

        simulation.run(runMoreGenerationsBindingModel.getGenerationCount());

        this.simulationRepository.save(simulation);
    }

    @Override
    public void deleteSimulationById(Long id) {
        this.simulationRepository.delete(id);
    }

    @Override
    public void resetSimulation(Long id) {
        Simulation simulation = this.simulationRepository.getSimulationById(id);

        Generation generation = simulation.getGenerations().get(0);
        generation.getGenerationMatchUpResults().clear();

        simulation.getGenerations().clear();
        simulation.getGenerations().add(generation);

        this.simulationRepository.save(simulation);
    }

    @Override
    public boolean ownsSimulation(User user, Long simId) {
        Simulation simulation = this.simulationRepository.getSimulationById(simId);
        if (simulation == null) {
            throw new SimulationNotFoundException();
        }

        for (Role role : user.getAuthorities()) {
            if (role.getAuthority().equals("ROLE_ADMIN")) {
                return true;
            }
        }

        if (simulation.getUser().getId() != user.getId()) {
            throw new UnauthorizedSimulationAccessException();
        }

        return true;
    }

    private Simulation getFullSimulationById(Long id) {
        Simulation simulation = this.simulationRepository.getSimulationById(id);
        if (simulation == null) {
            throw new SimulationNotFoundException();
        }

        //initialize strategy count and scores maps with appropriate key's
        for (Generation generation : simulation.getGenerations()) {
            for (StrategyImpl strategy : generation.getStrategies()) {
                if (!generation.getStrategyCount().containsKey(strategy.getName())) {
                    generation.getStrategyCount().put(strategy.getName(), 0);
                    generation.getStrategyScores().put(strategy.getName(), 0);
                }
                generation.getStrategyCount().put(strategy.getName(), generation.getStrategyCount().get(strategy.getName()) + 1);
            }
        }

        //do not initialize the last generation because it will write an empty map to it because there are
        //no scores saved in the DB yet that can be retrieved
        for (int i = 0; i < simulation.getGenerationCount() - 1; i++) {
            Map<String, Integer> strategyScores = this.generationService.getGenerationScoreMapByGenerationId(
                    simulation.getGenerations().get(i).getId());
            simulation.getGenerations().get(i).setStrategyScores(strategyScores);
        }

        return simulation;
    }

}
