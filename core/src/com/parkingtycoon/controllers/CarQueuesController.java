package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.CoordinateRotater;
import com.parkingtycoon.helpers.Random;

import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.CarQueueModel;
import com.parkingtycoon.models.PathFollowerModel;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class is responsible for providing a blueprint for any kind of Queue.
 */
public abstract class CarQueuesController extends UpdateableController {

    protected ArrayList<CarQueueModel> queues = new ArrayList<CarQueueModel>();
    protected int popInterval = 10;

    public CarQueuesController() {
        CompositionRoot.getInstance().simulationController.registerUpdatable(this);
    }

    public boolean addToQueue(CarModel car) {
        if (queues.size() == 0)
            return false;

        int maxQueueSize = Random.randomInt(3, 6); // some people don't like queues longer than 3, others 4 or 6

        // add car to random entrance queue
        Collections.shuffle(queues);
        for (CarQueueModel q : queues) {

            if (q.cars.size() <= maxQueueSize && sendCarToQueue(q, car) && q.cars.add(car)) {

                car.waitingInQueue = true;
                car.firstInQueue = false;
                car.queue = q;
                return true;
            }
        }
        return false;
    }

    private boolean sendCarToQueue(CarQueueModel queue, CarModel car) {

        int x = queue.x + CoordinateRotater.rotate(2, 3, 1, 3, queue.angle);
        int y = queue.y + CoordinateRotater.rotate(1, 3, 2, 3, queue.angle);

        PathFollowerModel.Goal goal = new PathFollowerModel.Goal(
                queue.floor, x, y,
                (int) car.position.x, (int) car.position.y
        ) {

            @Override
            public void arrived() {
                car.firstInQueue = true;
            }

            @Override
            public void failed() {
                car.firstInQueue = false;
                car.waitingInQueue = false;
                queue.removeCar(car);

                if (!addToQueue(car))
                    CompositionRoot.getInstance().carsController.sendToEndOfTheWorld(car);
            }

        };

        return CompositionRoot.getInstance().carsController.setGoal(car, goal);
    }

    @Override
    public void update() {
        for (CarQueueModel queue : queues) {

            for (CarModel car : queue.cars) {

                if (!car.firstInQueue)
                    continue;

                // this is the first car in the queue

                if (queue.popTimer++ >= popInterval) {

                    if (nextAction(car)) {

                        // car has new action, now remove from queue
                        queue.removeCar(car);
                        car.waitingInQueue = false;
                        car.firstInQueue = false;
                        car.queue = null;

                        //reset popTimer
                        queue.popTimer = 0;

                    } else queue.popTimer -= 5;
                }
                break;
            }
        }
    }

    protected abstract boolean nextAction(CarModel car);

}
