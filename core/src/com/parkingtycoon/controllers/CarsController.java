package com.parkingtycoon.controllers;

import com.parkingtycoon.models.CarModel;

public class CarsController extends PathFollowerController<CarModel> {

    @Override
    public void update() {
        super.update();

        for (CarModel car : pathFollowers)
            car.notifyViews();
    }

    public CarModel createCar() {

        CarModel car = new CarModel();
        pathFollowers.add(car);
        return car;

    }
}
