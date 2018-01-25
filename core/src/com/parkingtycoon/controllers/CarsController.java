package com.parkingtycoon.controllers;

import com.parkingtycoon.models.CarModel;

/**
 * This class is responsible for controlling the Cars in the simulation.
 */
public class CarsController extends PathFollowerController<CarModel> {

    @Override
    public void update() {
        super.update();

        for (CarModel car : pathFollowers)  // TODO: a controller should no be able to directly call the notifyViews function
            car.notifyViews();
    }

    public CarModel createCar() {

        CarModel car = new CarModel();
        pathFollowers.add(car);
        return car;

    }

}
