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

            if (q.cars.size() <= maxQueueSize && q.cars.add(car)) {

                int x = q.x + CoordinateRotater.rotate(2, 3, 1, 3, q.angle);
                int y = q.y + CoordinateRotater.rotate(1, 3, 1, 3, q.angle);

                car.setGoal(new PathFollowerModel.Goal(q.floor, x, y) {
                    @Override
                    public void arrived() {
//                        car.position.set(q.x + .5f, q.y + .5f);
                    }

                    @Override
                    public void failed() {
                        addToQueue(car); // todo
                    }
                });
                car.waitingInQueue = true;

                return true;
            }

        }

        return false;
    }

    @Override
    public void update() {
        for (CarQueueModel queue : queues) {

            for (CarModel car : queue.cars) {

                if (car.getPath() != null)
                    continue;

                // this is the first car in the queue

                if (queue.popTimer++ >= popInterval) {

                    if (nextAction(car)) {

                        // car has new action, now remove from queue
                        queue.removeCar(car);
                        car.waitingInQueue = false;

                        //reset popTimer
                        queue.popTimer = 0;
                    }

                }

                break;
            }
        }
    }

    protected abstract boolean nextAction(CarModel car);


}
