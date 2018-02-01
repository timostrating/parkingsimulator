package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.CoordinateRotater;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.CarQueueModel;

/**
 * This class is responsible for providing a Queue that processes the Cars that would like to leave the park.
 */
public class ExitsController extends CarQueuesController {

    public ExitsController() {
        popInterval = 100;
    }

    @Override
    protected boolean nextAction(CarModel car) {
        CompositionRoot root = CompositionRoot.getInstance();
        root.financialController.addAmount(car.vip ? 200 : 100); // todo: change amount

        int fromX = car.queue.x + CoordinateRotater.rotate(0, 3, 1, 3, car.queue.angle);
        int fromY = car.queue.y + CoordinateRotater.rotate(1, 3, 0, 3, car.queue.angle);

        return root.carsController.sendToEndOfTheWorld(car, fromX, fromY, false);
    }

    public CarQueueModel createExit(int x, int y, int angle, int floor) {
        CarQueueModel exit = new CarQueueModel(x, y, angle, floor, true, true);
        createViews(exit, "exit");
        queues.add(exit);
        return exit;
    }
}
