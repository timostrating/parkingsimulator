package com.parkingtycoon;

import javafx.stage.Stage;
import com.parkingtycoon.controllers.ApplicationController;
import com.parkingtycoon.controllers.ChartsController;

/**
 *
 * The CompositionRoot manages all the controllers.
 *
 */
public class CompositionRoot {

    private static CompositionRoot instance;

    public static CompositionRoot getInstance() {
        return instance;
    }

    static void init(Stage stage) {
        if (instance == null) {
            instance = new CompositionRoot(stage);
            instance.start();
        }
    }

    public Stage stage;
    public ApplicationController applicationController;
    public ChartsController chartsController;

    private CompositionRoot(Stage stage) {
        this.stage = stage;
    }

    private void start() {
        applicationController = new ApplicationController();
        chartsController = new ChartsController();
    }

}
