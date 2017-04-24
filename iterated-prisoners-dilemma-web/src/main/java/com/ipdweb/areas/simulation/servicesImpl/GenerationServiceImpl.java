package com.ipdweb.areas.simulation.servicesImpl;

import com.ipdweb.areas.simulation.repositories.GenerationRepository;
import com.ipdweb.areas.simulation.services.GenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GenerationServiceImpl implements GenerationService {

    private GenerationRepository generationRepository;

    @Autowired
    public GenerationServiceImpl(GenerationRepository generationRepository) {
        this.generationRepository = generationRepository;
    }

    @Override
    public Map<String, Integer> getGenerationScoreMapByGenerationId(Long id) {
        Object[] generationResultsData = this.generationRepository.getGenerationResultsByGenerationId(id);

        Map<String, Integer> strategyScores = new HashMap<>();

        for (Object generationResults : generationResultsData) {
            Object[] data = (Object[]) generationResults;
            String stratAName = (String) data[0];
            int stratAScore = (int) (long) data[1];
            String stratBName = (String) data[2];
            int stratBScore = (int) (long) data[3];

            if (!strategyScores.containsKey(stratAName)) {
                strategyScores.put(stratAName, 0);
            }
            if (!strategyScores.containsKey(stratBName)) {
                strategyScores.put(stratBName, 0);
            }

            int score = strategyScores.get(stratAName) + stratAScore;
            strategyScores.put(stratAName, score);

            score = strategyScores.get(stratBName) + stratBScore;
            strategyScores.put(stratBName, score);
        }

        return strategyScores;
    }
}
