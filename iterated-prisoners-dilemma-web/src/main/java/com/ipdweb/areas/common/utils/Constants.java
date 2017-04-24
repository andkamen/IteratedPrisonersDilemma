package com.ipdweb.areas.common.utils;

public class Constants {

    public static final int MAX_GENERATION_ROUND_COUNT = 60;
    public static final int MIN_GENERATION_ROUND_COUNT = 50;

    public static final int MAX_TOURNAMENT_ROUND_COUNT = 100;
    public static final int MIN_TOURNAMENT_ROUND_COUNT = 90;

    //Round Points depending on outcome. From A's perspective. A vs B . points awarded to A
    public static final int COOPERATE_COOPERATE_SCORE = 3;
    public static final int COOPERATE_DEFECT_SCORE = 0;
    public static final int DEFECT_COOPERATE_SCORE = 5;
    public static final int DEFECT_DEFECT_SCORE = 1;

    //strategy factory
    public static final String STRATEGY_PACKAGE = "com.ipdweb.areas.strategy.entities.strategies.";
    public static final String STRATEGY_SUFFIX = "Strategy";

    //Roles
    public static final String DEFAULT_ROLE = "ROLE_USER";
    public static final String ADMIN_ROLE = "ROLE_ADMIN";

    //ContentValidator
    public static final String VIEW_TOURNAMENTS_URI = "/tournaments";
    public static final String CREATE_TOURNAMENTS_URI = "/tournaments/create";
    public static final String VIEW_SIMULATION_URI = "/simulations";
    public static final String CREATE_SIMULATION_URI = "/simulations/create";
    public static final int USER_ID_POSITION = 2;


}
