package com.parkingtycoon;

import com.parkingtycoon.controllers.FloorsController;
import com.parkingtycoon.controllers.SimulationController;
import com.parkingtycoon.helpers.Logger;


/**
 * This is the Root object of our project
 * see the CompositionRoot design pattern for more info
 * http://www.dotnetcurry.com/patterns-practices/1285/clean-composition-roots-dependency-injection
 */

public class CompositionRoot {
    public SimulationController simulationController;
    public FloorsController floorsController;


    private static CompositionRoot instance;

    public static CompositionRoot getInstance() {
        if (instance == null) {
            instance = new CompositionRoot();
        }

        return instance;
    }

    public static void init() {
        getInstance().start();
    }


    private CompositionRoot() {
        Logger.info("CompositionRoot is created");
    }

    private void start() {
        simulationController = new SimulationController();
        floorsController = new FloorsController();
    }
}
