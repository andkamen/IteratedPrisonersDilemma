package com.ipdweb.areas.simulation.servicesImpl;


import com.ipdweb.areas.simulation.entities.Generation;
import com.ipdweb.areas.simulation.entities.Simulation;
import com.ipdweb.areas.simulation.models.bindingModels.CreateSimulationBindingModel;
import com.ipdweb.areas.simulation.models.viewModels.SimulationPreviewViewModel;
import com.ipdweb.areas.simulation.repositories.SimulationRepository;
import com.ipdweb.areas.simulation.services.GenerationService;
import com.ipdweb.areas.simulation.services.SimulationService;
import com.ipdweb.areas.strategy.entities.StrategyImpl;
import com.ipdweb.areas.strategy.factories.StrategyFactory;
import com.ipdweb.areas.strategy.factories.StrategyFactoryImpl;
import com.ipdweb.areas.strategy.services.StrategyService;
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
    public Simulation getSimulationById(Long id) {
        Simulation simulation = this.simulationRepository.getSimulationById(id);

        //initialize strategy count and scores maps with appropriate key's
        for (Generation generation : simulation.getGenerations()) {
            for (StrategyImpl strategy : generation.getStrategies()) {
                if (!generation.getStrategyCount().containsKey(strategy.getName())) {
                    generation.getStrategyCount().put(strategy.getName(), 0);
                    // generation.getStrategyScores().put(strategy.getName(), 0);
                }
                generation.getStrategyCount().put(strategy.getName(), generation.getStrategyCount().get(strategy.getName()) + 1);
            }
        }

        for (int i = 0; i < simulation.getGenerationCount(); i++) {
            Map<String, Integer> strategyScores = this.generationService.getGenerationScoreMapByGenerationId(
                    simulation.getGenerations().get(i).getId());
            simulation.getGenerations().get(i).setStrategyScores(strategyScores);
        }

        return simulation;
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
    public void deleteSimulationById(Long id) {
        this.simulationRepository.delete(id);
    }

    @Override
    public Set<SimulationPreviewViewModel> getAllSimulations(User user) {

        Set<Simulation> allSimulations = this.simulationRepository.getAllSimulations(user);
        Set<SimulationPreviewViewModel> simulations = new LinkedHashSet<>();


        for (Simulation sim : allSimulations) {
            SimulationPreviewViewModel simulationPreviewModel = this.modelMapper.map(sim, SimulationPreviewViewModel.class);

            //Create Map with count of each strategy
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


}
