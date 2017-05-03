package com.ipdweb.areas.strategy.repositories;

import com.ipdweb.areas.strategy.entities.StrategyImpl;
import com.ipdweb.areas.strategy.entities.strategies.AlwaysCooperateStrategy;
import com.ipdweb.areas.strategy.entities.strategies.AlwaysDefectStrategy;
import com.ipdweb.areas.strategy.entities.strategies.FirmButFairStrategy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class StrategyRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private StrategyRepository strategyRepository;

    @Before
    public void setUp() throws Exception {
        //Arrange
        StrategyImpl strategy = new AlwaysCooperateStrategy();
        StrategyImpl strategy1 = new AlwaysDefectStrategy();
        StrategyImpl strategy2 = new FirmButFairStrategy();

        this.em.persist(strategy);
        this.em.persist(strategy1);
        this.em.persist(strategy2);
    }


    @Test
    public void getAllStrategies_ShouldReturnList() throws Exception {
        //Act
        List<StrategyImpl> strategies = this.strategyRepository.getAllStrategies();

        //Assert
        assertEquals(3, strategies.size());


    }

}