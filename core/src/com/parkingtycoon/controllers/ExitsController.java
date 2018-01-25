package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.CarQueueModel;
import com.parkingtycoon.views.EntranceView;

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
        exit.registerView(new EntranceView());
        exit.notifyViews();
        queues.add(exit);
        return exit;
    }
}
