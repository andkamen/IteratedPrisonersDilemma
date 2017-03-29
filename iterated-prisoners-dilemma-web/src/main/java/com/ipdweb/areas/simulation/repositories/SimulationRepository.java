package com.ipdweb.areas.simulation.repositories;

import com.ipdweb.areas.simulation.entities.Simulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulationRepository extends JpaRepository<Simulation,Long> {

    Simulation getSimulationById(Long id);

  }
