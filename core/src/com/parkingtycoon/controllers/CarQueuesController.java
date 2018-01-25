package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.Random;
import com.parkingtycoon.helpers.UpdateableController;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.CarQueueModel;

import java.util.ArrayList;
import java.util.Collections;

public abstract class CarQueuesController extends UpdateableController {

    protected ArrayList<CarQueueModel> queues = new ArrayList<CarQueueModel>();
    protected int popInterval = 10;

    public CarQueuesController() {
        CompositionRoot.getInstance().simulationController.registerUpdatable(this);
    }

    public boolean addToQueue(CarModel car) {
        if (queues.size() == 0)
            return false;

        int maxQueueSize = Random.randomInt(8, 12); // some people don't like queues longer than 8, others 10 or 12

        // add car to random entrance queue
        Collections.shuffle(queues);
        for (CarQueueModel q : queues) {

            if (q.cars.size() <= maxQueueSize && q.cars.add(car)) {

                CompositionRoot.getInstance().floorsController.sendCarTo(0, q.x, q.y, car);

                return true;
            }

        }

        return false;
    }

    @Override
    public void update() {
        for (CarQueueModel queue : queues) {

            for (CarModel car : queue.cars) {

                if (car.path != null) continue;

                // this is the first car in the queue

                if (queue.popTimer++ >= popInterval) {

                    if (nextAction(car)) {

                        // car has new action, now remove from queue
                        queue.cars.remove(0);
                        queue.notifyViews(); // play open animation

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
