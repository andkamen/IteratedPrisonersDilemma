package com.ipdweb.areas.simulation.repositories;

import com.ipdweb.areas.simulation.entities.Generation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenerationRepository extends JpaRepository<Generation, Long> {


//    @Query(nativeQuery = true, value = "Select generation_match_up_results.strat_a_name  ,Sum(generation_match_up_results.strat_a_score), generation_match_up_results.strat_b_name,Sum(generation_match_up_results.strat_b_score) " +
//            "From generations " +
//            "JOIN generation_match_up_results on generations.id = generation_match_up_results.generation_id " +
//            "WHERE generations.id = :id " +
//            "GROUP BY generation_match_up_results.strat_a_name, generation_match_up_results.strat_b_name")
//
//        @Query(value = "SELECT g from Generation g join g.generationMatchUpResults gm " +
//            "where g.id =:id " +
//            "GROUP BY g ")
//    Generation getGenerationResultsByGenerationId(@Param("id") Long id);

    //TODO gm doesn't suggest anything ;/

}
