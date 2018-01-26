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

//        CarModel car = CompositionRoot.getInstance().carsController.createCar();
//        car.startTime = 0;
//        car.endTime = 66666666666666L;
//        car.registerView(new CarView());
//        CompositionRoot.getInstance().entrancesController.addToQueue(car);
//
//        car = CompositionRoot.getInstance().carsController.createCar();
//        car.startTime = 0;
//        car.endTime = 66666666666666L;
//        car.registerView(new CarView());
//        CompositionRoot.getInstance().entrancesController.addToQueue(car);

        return entrance;
    }

}
