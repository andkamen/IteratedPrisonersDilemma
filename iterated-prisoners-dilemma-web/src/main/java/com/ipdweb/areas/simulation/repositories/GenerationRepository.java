package com.ipdweb.areas.simulation.repositories;

import com.ipdweb.areas.simulation.entities.Generation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GenerationRepository extends JpaRepository<Generation, Long> {

    @Query(value = "SELECT gm.stratAName,SUM (gm.stratAScore), gm.stratBName, SUM(gm.stratBScore)" +
            "from Generation g join g.generationMatchUpResults gm " +
            "where g.id =:id " +
            "GROUP BY gm.stratAName,gm.stratBName")
    Object[] getGenerationResultsByGenerationId(@Param("id") Long id);
}
