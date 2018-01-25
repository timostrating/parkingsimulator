package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.Random;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.CarQueueModel;

import java.util.ArrayList;

/**
 * This class is responsible for providing a Queue that processes the new Cars that would like to park.
 */
public class EntrancesController extends CarQueuesController {

    private static final int POP_INTERVAL = 10;

    private ArrayList<CarQueueModel> entrances = new ArrayList<CarQueueModel>(){{
        add(new CarQueueModel());
    }};

    @Override
    public boolean addToQueue(CarModel car) {
        if (entrances.size() == 0)
            return false;

        // add car to random entrance queue
        return Random.choice(entrances).cars.add(car);
    }

    @Override
    public void update() {
        for (CarQueueModel entrance : entrances) {
            if (entrance.cars.size() == 0)
                continue;

            if (entrance.popTimer++ >= POP_INTERVAL) {

                // pop car
                CarModel car = entrance.cars.get(0);

                // try to find a place for the car:
                if (CompositionRoot.getInstance().floorsController.parkCar(car)) {

                    // car succesfully parked, now remove from entranceQueue
                    entrance.cars.remove(0);

                    //reset popTimer
                    entrance.popTimer = 0;
                }

            }
        }

    }

}
