package com.ipdweb.areas.strategy.repositories;

import com.ipdweb.areas.strategy.entities.StrategyImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface StrategyRepository extends JpaRepository<StrategyImpl, Long> {
    StrategyImpl getById(Long id);

    StrategyImpl getByName(String name);

    @Query(value = "select s from StrategyImpl as s order by s.id")
    Set<StrategyImpl> findAllStrategies();
}
