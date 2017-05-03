package com.ipdweb.areas.strategy.entities;

import com.ipdweb.areas.strategy.entities.interfaces.Strategy;

import javax.persistence.*;


@Entity
@Table(name = "Strategy")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class StrategyImpl implements Strategy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public StrategyImpl() {
    }

    public StrategyImpl(String name, String description) {
        this.setName(name);
        this.setDescription(description);
    }

    @Override
    public boolean makeMove() {
        return false;
    }

    @Override
    public void resetStrategy() {

    }

    @Override
    public void updateOpponentMove(boolean opponentMove) {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
