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

    @Override
    protected boolean nextAction(CarModel car) {
        int fromX = car.queue.x + CoordinateRotater.rotate(0, 3, 1, 3, car.queue.angle);
        int fromY = car.queue.y + CoordinateRotater.rotate(1, 3, 0, 3, car.queue.angle);
        return CompositionRoot.getInstance().carsController.parkCar(car, fromX, fromY);
    }

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
