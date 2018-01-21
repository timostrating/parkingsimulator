package com.parkingtycoon;

import com.parkingtycoon.controllers.FloorsController;
import com.parkingtycoon.controllers.RenderController;
import com.parkingtycoon.controllers.SimulationController;
import com.parkingtycoon.helpers.Logger;


/**
 * This is the Root object of our project
 * see the CompositionRoot design pattern for more info
 * http://www.dotnetcurry.com/patterns-practices/1285/clean-composition-roots-dependency-injection
 */

public class CompositionRoot {
    public Game game;
    public SimulationController simulationController;
    public RenderController renderController;
    public FloorsController floorsController;


    private static CompositionRoot instance;

    public static CompositionRoot getInstance() {
        if (instance == null) {
            instance = new CompositionRoot();
        }

        return instance;
    }


    public static void init(Game game) {
        getInstance().start(game);
    }


    private CompositionRoot() {

        Logger.info("CompositionRoot is created");
    }


    private void start(Game game) {
        this.game = game;
        simulationController = new SimulationController();
        renderController = new RenderController(game);
        floorsController = new FloorsController();
    }
}
