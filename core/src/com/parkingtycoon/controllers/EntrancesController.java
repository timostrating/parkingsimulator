package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.CoordinateRotater;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.CarQueueModel;

import java.util.EnumSet;

/**
 * This class is responsible for providing a Queue that processes the new Cars that would like to park.
 */
public class EntrancesController extends CarQueuesController {

    /**
     * This method will try to park the car after it has waited long enough in the queue.
     *
     * @param car   The car that has waited long enough
     * @return      Whether or not a parking place was found
     */
    @Override
    protected boolean nextAction(CarModel car) {
        int fromX = car.queue.x + CoordinateRotater.rotate(0, 3, 1, 3, car.queue.angle);
        int fromY = car.queue.y + CoordinateRotater.rotate(1, 3, 0, 3, car.queue.angle);
        return CompositionRoot.getInstance().carsController.parkCar(car, fromX, fromY);
    }

    /**
     * This method will create a new entrance that can be used by cars
     *
     * @param x       The x-position of the entrance
     * @param y       The y-position of the entrance
     * @param angle   The angle of the new entrance
     * @param floor   The floor the entrace has to stand on
     * @param carType The type of car this entrance can process
     * @return        The newly created entrance
     */
    public CarQueueModel createEntrance(int x, int y, int angle, int floor, CarModel.CarType carType) {
        CarQueueModel entrance = new CarQueueModel(x, y, angle, floor, EnumSet.of(carType));
        createViews(
                entrance,
                carType == CarModel.CarType.AD_HOC ? "enter"
                        : (carType == CarModel.CarType.VIP ? "vip"
                        : "reserved")
        );
        // entrances for vips and reserved cars have licence plate recognition
        entrance.popInterval = carType == CarModel.CarType.AD_HOC ? 50 : 5;
        entrance.maxQueueLength = carType == CarModel.CarType.AD_HOC ? 6 : 30;
        queues.add(entrance);
        return entrance;
    }

}
