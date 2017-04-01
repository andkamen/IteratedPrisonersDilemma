package com.ipdweb.areas.simulation.repositories;

import com.ipdweb.areas.simulation.entities.Simulation;
import com.ipdweb.areas.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SimulationRepository extends JpaRepository<Simulation,Long> {

    Simulation getSimulationById(Long id);

    @Query(value = "SELECT g.id, gm.stratAName,SUM (gm.stratAScore), gm.stratBName, SUM(gm.stratBScore)" +
            "from Simulation as s join s.generations as g join g.generationMatchUpResults gm " +
            "where s.id =:id " +
            "GROUP BY g.id, gm.stratAName,gm.stratBName")
    Object[] getGenerationResultsBySimulationId(@Param("id") Long id);

    @Query(value = "select s from Simulation as s where s.user = :user order by s.id ")
    Set<Simulation> getAllSimulations(@Param("user") User user);

  }
