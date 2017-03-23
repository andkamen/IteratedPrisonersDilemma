package com.simulation.contracts;

/**
 * Interface that each Prisoner's Dilema Strategy should implement
 */
public interface Strategy {

    /**
     * Contains the logic of the strategy. The boolean it returns is the decision.
     * true -> it will cooperate
     * false -> it won't
     */
    boolean makeMove();

    /**
     * Resets the strategy to base behaviour.
     */
    void resetStrategy();

    /**
     * Updates the strategy's info about the last opponent's moves. The strategy might base it's next move decision
     * on this information.
     *
     * @param opponentMove - The information about the last move of the opponent. (True - cooperated/ False - it didn't)
     */
    void updateOpponentMove(boolean opponentMove);
}
