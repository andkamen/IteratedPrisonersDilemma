package com.simulation.models;

import com.simulation.contracts.MatchUpResult;

public class MatchUpResultImpl implements MatchUpResult {

    private String stratAName;
    private String stratBName;
    private boolean[] allStratAMoves;
    private boolean[] allStratBMoves;
    private int stratAScore;
    private int stratBScore;

    public MatchUpResultImpl(String stratAName, String stratBName, boolean[] allStratAMoves, boolean[] allStratBMoves, int stratAScore, int stratBScore) {
        this.stratAName = stratAName;
        this.stratBName = stratBName;
        this.allStratAMoves = allStratAMoves;
        this.allStratBMoves = allStratBMoves;
        this.stratAScore = stratAScore;
        this.stratBScore = stratBScore;
    }

    public int getStratAScore() {
        return this.stratAScore;
    }

    public int getStratBScore() {
        return this.stratBScore;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%s (A) vs %s (B)", this.stratAName.replaceAll("Strategy", ""), this.stratBName.replaceAll("Strategy", "")))
                .append(System.lineSeparator());

        sb.append("Round");
        for (int i = 1; i <= this.allStratAMoves.length; i++) {
            sb.append(String.format("%4d", i));
        }
        sb.append(System.lineSeparator());

        sb.append(String.format("%5s", "A:"));
        for (boolean stratAMove : allStratAMoves) {
            sb.append(String.format("%4s", stratAMove ? "C" : "D"));
        }
        sb.append(System.lineSeparator());

        sb.append(String.format("%5s", "B:"));
        for (boolean stratBMove : allStratBMoves) {
            sb.append(String.format("%4s", stratBMove ? "C" : "D"));
        }
        sb.append(System.lineSeparator());

        sb.append(String.format("%s -> %d || %s -> %d", this.stratAName.replaceAll("Strategy", ""), this.stratAScore,
                this.stratBName.replaceAll("Strategy", ""), this.stratBScore))
                .append(System.lineSeparator());


        return sb.toString();
    }
}
