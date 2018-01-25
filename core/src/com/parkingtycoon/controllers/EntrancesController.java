package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.CarQueueModel;
import com.parkingtycoon.views.EntranceView;

public class EntrancesController extends CarQueuesController {

    public EntrancesController() {
        popInterval = 200;
    }

    @Override
    protected boolean nextAction(CarModel car) {
        return CompositionRoot.getInstance().floorsController.parkCar(car);
    }

    public CarQueueModel createEntrance(int x, int y) {
        CarQueueModel entrance = new CarQueueModel(x, y);
        entrance.registerView(new EntranceView());
        entrance.notifyViews();
        queues.add(entrance);
        return entrance;
    }

}
