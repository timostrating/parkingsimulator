package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.CarQueueModel;
import com.parkingtycoon.views.EntranceView;

/**
 * This class is responsible for providing a Queue that processes the new Cars that would like to park.
 */
public class EntrancesController extends CarQueuesController {

    public EntrancesController() {
        popInterval = 200;
    }

    @Override
    protected boolean nextAction(CarModel car) {
        return CompositionRoot.getInstance().carsController.parkCar(car);
    }

    public CarQueueModel createEntrance(int x, int y, int angle, int floor) {
        CarQueueModel entrance = new CarQueueModel(x, y, angle, floor);
        EntranceView entranceView = new EntranceView();
        entranceView.show();
        entrance.registerView(entranceView);
        queues.add(entrance);
        return entrance;
    }

}
