package com.ipdweb.repositories;

import com.ipdweb.entities.Simulation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulationRepository extends CrudRepository<Simulation,Long> {

}
