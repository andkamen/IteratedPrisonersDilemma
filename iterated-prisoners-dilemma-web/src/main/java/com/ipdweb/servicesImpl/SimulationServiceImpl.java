package com.ipdweb.servicesImpl;


import com.ipdweb.entities.Simulation;
import com.ipdweb.repositories.SimulationRepository;
import com.ipdweb.services.SimulationService;
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
}
