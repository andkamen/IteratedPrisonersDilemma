package com.ipdweb.repositories;

import com.ipdweb.entities.strategy.StrategyImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrategyRepository extends JpaRepository<StrategyImpl,Long> {
    StrategyImpl getById(Long id);

    StrategyImpl getByName(String name);
}
