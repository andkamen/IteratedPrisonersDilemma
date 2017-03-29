package com.ipdweb.areas.simulation.repositories;

import com.ipdweb.areas.simulation.entities.GenerationMatchUpResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenerationMatchUpResultRepository extends JpaRepository<GenerationMatchUpResult,Long> {

//    @Query(value = "SELECT g from GenerationMatchUpResult ")
//   Object[] getGenerationResultsByGeneration(@Param("id") Long id);

  }
