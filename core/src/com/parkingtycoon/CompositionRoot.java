package com.parkingtycoon;

import com.parkingtycoon.controllers.*;
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
    public AnimatedSpritesController animatedSpritesController;
    public CarsController carsController;
    public EntrancesController entrancesController;
    public ExitsController exitsController;
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
        Logger.info("CompositionRoot is created\nEnjoy ParkingSimulator Tycoon");
    }


    private void start(Game game) {
        this.game = game;
        simulationController = new SimulationController();
        renderController = new RenderController(game);
        animatedSpritesController = new AnimatedSpritesController();
        carsController = new CarsController();
        entrancesController = new EntrancesController();
        exitsController = new ExitsController();
        floorsController = new FloorsController();

        simulationController.startSimulation();
    }
}
