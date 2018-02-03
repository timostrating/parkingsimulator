package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.CoordinateRotater;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.CarQueueModel;

import java.util.EnumSet;

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

        int fromX = car.queue.x + CoordinateRotater.rotate(0, 3, 1, 3, car.queue.angle);
        int fromY = car.queue.y + CoordinateRotater.rotate(1, 3, 0, 3, car.queue.angle);

        if (root.carsController.sendToEndOfTheWorld(car, fromX, fromY, false)) {
            root.financialController.addAmount(
                    car.carType == CarModel.CarType.AD_HOC ? 200
                            : car.carType == CarModel.CarType.RESERVED ? 300
                            : 0
            );
            return true;
        }
        return false;
    }

    public CarQueueModel createExit(int x, int y, int angle, int floor) {
        CarQueueModel exit = new CarQueueModel(x, y, angle, floor,
                EnumSet.of(CarModel.CarType.AD_HOC, CarModel.CarType.VIP, CarModel.CarType.RESERVED)
        );
        createViews(exit, "exit");
        queues.add(exit);
        return exit;
    }
}
