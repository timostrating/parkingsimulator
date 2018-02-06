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

    /**
     * This method will make cars drive away to the end of the world after they have paid.
     *
     * @param car   The car that has waited long enough
     * @return      Whether or not the car is able to drive away
     */
    @Override
    protected boolean nextAction(CarModel car) {
        CompositionRoot root = CompositionRoot.getInstance();

        int fromX = car.queue.x + CoordinateRotater.rotate(0, 3, 1, 3, car.queue.angle);
        int fromY = car.queue.y + CoordinateRotater.rotate(1, 3, 0, 3, car.queue.angle);

        if (root.carsController.sendToEndOfTheWorld(car, fromX, fromY, false)) {
            root.financialController.addCarPayment(car.carType);
            return true;
        }
        return false;
    }

    /**
     * This method will create a new exit that can be used by cars
     *
     * @param x       The x-position of the exit
     * @param y       The y-position of the exit
     * @param angle   The angle of the new exit
     * @param floor   The floor the exit has to stand on
     * @return        The newly created exit
     */
    public CarQueueModel createExit(int x, int y, int angle, int floor) {
        CarQueueModel exit = new CarQueueModel(x, y, angle, floor,
                EnumSet.of(CarModel.CarType.AD_HOC, CarModel.CarType.VIP, CarModel.CarType.RESERVED)
        );
        exit.popInterval = 100;
        exit.maxQueueLength = 15;
        createViews(exit, "exit");
        queues.add(exit);
        return exit;
    }
}
