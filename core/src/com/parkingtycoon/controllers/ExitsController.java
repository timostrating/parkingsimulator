package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.CarQueueModel;
import com.parkingtycoon.views.EntranceView;

/**
 * This class is responsible for providing a Queue that processes the Cars that would like to leave the park.
 */
public class ExitsController extends CarQueuesController {

    public ExitsController() {
        popInterval = 450;
    }

    @Override
    protected boolean nextAction(CarModel car) {
        CompositionRoot.getInstance().floorsController.sendCarAway(car);
        return true;
    }

    public CarQueueModel createExit(int x, int y) {
        CarQueueModel exit = new CarQueueModel(x, y);
        EntranceView view = new EntranceView(); // todo: make ExitView
        view.show();
        exit.registerView(view);
        queues.add(exit);
        return exit;
    }
}
