package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.Logger;
import com.parkingtycoon.helpers.Random;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.CarQueueModel;
import com.parkingtycoon.views.EntranceView;

import java.util.ArrayList;
import java.util.Collections;

public class EntrancesController extends CarQueuesController {

    private static final int POP_INTERVAL = 10;

    private ArrayList<CarQueueModel> entrances = new ArrayList<CarQueueModel>();

    @Override
    public boolean addToQueue(CarModel car) {
        if (entrances.size() == 0)
            return false;

        int maxQueueSize = Random.randomInt(8, 12); // some people don't like queues longer than 8, others 10 or 12

        // add car to random entrance queue
        Collections.shuffle(entrances);
        for (CarQueueModel e : entrances) {

            if (e.cars.size() <= maxQueueSize && e.cars.add(car)) {

                CompositionRoot.getInstance().floorsController.sendCarTo(0, e.x, e.y, car);

                return true;
            }

        }

        return false;
    }

    @Override
    public void update() {
        for (CarQueueModel entrance : entrances) {

            for (CarModel car : entrance.cars) {

                if (car.path == null) continue;

                // this is the first car in the queue

                if (entrance.popTimer++ >= POP_INTERVAL) {

                    // try to find a place for the car:
                    if (CompositionRoot.getInstance().floorsController.parkCar(car)) {

                        // car succesfully parked, now remove from entranceQueue
                        entrance.cars.remove(0);
                        entrance.notifyViews(); // play open animation

                        //reset popTimer
                        entrance.popTimer = 0;
                    }

                }

                break;
            }
        }
    }

    public CarQueueModel createEntrance(int x, int y) {

        Logger.info("heeeeeeeeeeeeee");

        CarQueueModel entrance = new CarQueueModel(x, y);
        entrance.registerView(new EntranceView());
        entrance.notifyViews();
        entrances.add(entrance);
        return entrance;
    }

}
