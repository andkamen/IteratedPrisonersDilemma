package com.ipdweb.areas.simulation.servicesImpl;


import com.ipdweb.areas.simulation.entities.Generation;
import com.ipdweb.areas.simulation.entities.GenerationMatchUpResult;
import com.ipdweb.areas.simulation.entities.Simulation;
import com.ipdweb.areas.strategy.entities.StrategyImpl;
import com.ipdweb.areas.simulation.repositories.SimulationRepository;
import com.ipdweb.areas.simulation.services.SimulationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimulationServiceImpl implements SimulationService {

    @Autowired
    private SimulationRepository simulationRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public void save(Simulation simulation) {
        this.simulationRepository.save(simulation);
    }

    @Override
    public Simulation getSimulationById(Long id) {
        Simulation simulation = this.simulationRepository.getSimulationById(id);

        for (Generation generation : simulation.getGenerations()) {

            //initialize strategy count and scores maps with appropriate key's
            for (StrategyImpl strategy : generation.getStrategies()) {
                if (!generation.getStrategyCount().containsKey(strategy.getName())) {
                    generation.getStrategyCount().put(strategy.getName(), 0);
                    generation.getStrategyScores().put(strategy.getName(), 0);
                }
                generation.getStrategyCount().put(strategy.getName(), generation.getStrategyCount().get(strategy.getName()) + 1);
            }

            //fill in values for strategy scores
            //TODO prettify
            for (GenerationMatchUpResult matchUpResult : generation.getGenerationMatchUpResults()) {
                int strategyScore = generation.getStrategyScores().get(matchUpResult.getStratAName()) + matchUpResult.getStratAScore();
                generation.getStrategyScores().put(matchUpResult.getStratAName(), strategyScore);

                strategyScore = generation.getStrategyScores().get(matchUpResult.getStratBName()) + matchUpResult.getStratBScore();
                generation.getStrategyScores().put(matchUpResult.getStratBName(), strategyScore);
            }
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
}
