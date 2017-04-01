package com.ipdweb.areas.simulation.services;

import java.util.Map;

public interface GenerationService {
    Map<String, Integer> getGenerationScoreMapByGenerationId(Long id);
}
