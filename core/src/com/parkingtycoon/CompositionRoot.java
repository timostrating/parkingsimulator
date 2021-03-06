package com.parkingtycoon;

import com.parkingtycoon.controllers.*;
import com.parkingtycoon.controllers.ui.HudController;
import com.parkingtycoon.helpers.Logger;

/**
 * This is the Root object of our project
 * see the CompositionRoot design pattern for more info
 * http://www.dotnetcurry.com/patterns-practices/1285/clean-composition-roots-dependency-injection
 */
public class CompositionRoot {

    public InputController inputController;
    public SimulationController simulationController;
    public RenderController renderController;
    public TimeController timeController;
    public AnimatedSpritesController animatedSpritesController;
    public EntrancesController entrancesController;
    public ExitsController exitsController;
    public FloorsController floorsController;
    public CarsController carsController;
    public FinancialController financialController;
    public BluePrintsController bluePrintsController;
    public ElevatorsController elevatorsController;
    public HudController hudController;

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
        inputController = new InputController(game);                     // Setup
        simulationController = new SimulationController();
        renderController = new RenderController(game);
        timeController = new TimeController();
        animatedSpritesController = new AnimatedSpritesController();     // Cars
        entrancesController = new EntrancesController();
        exitsController = new ExitsController();
        bluePrintsController = new BluePrintsController();
        floorsController = new FloorsController();
        carsController = new CarsController();
        financialController = new FinancialController();                 // UI
        bluePrintsController = new BluePrintsController();
        elevatorsController = new ElevatorsController();
        hudController = new HudController(game);
    }

}
