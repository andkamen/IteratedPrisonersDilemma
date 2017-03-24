package com.ipdweb.repositories;

import com.ipdweb.entities.strategy.StrategyImpl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrategyRepository extends CrudRepository<StrategyImpl,Long> {
    StrategyImpl getById(Long id);

    StrategyImpl getByName(String name);
}
