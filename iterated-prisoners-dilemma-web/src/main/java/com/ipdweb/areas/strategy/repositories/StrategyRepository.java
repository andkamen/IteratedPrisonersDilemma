package com.ipdweb.areas.strategy.repositories;

import com.ipdweb.areas.strategy.entities.StrategyImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StrategyRepository extends JpaRepository<StrategyImpl, Long> {

    @Query(value = "select s from StrategyImpl as s order by s.id")
    List<StrategyImpl> getAllStrategies();
}
