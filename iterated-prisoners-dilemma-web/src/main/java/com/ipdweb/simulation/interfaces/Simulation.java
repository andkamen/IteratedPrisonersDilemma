package com.ipdweb.simulation.interfaces;

public interface Simulation extends AddStrategy, RemoveStrategy{

    String getName();

    int getGenerationSize();

    void run(int generationCount);

    String print(int genNum, boolean isVerboseMode);
}
