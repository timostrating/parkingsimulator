package com.parkingtycoon.models;

import java.util.ArrayList;

/**
 * This Class is responsible for storing a Queue of Cars.
 */
public class CarQueueModel extends BuildableModel {

    public int popTimer; // when popTimer == POP_INTERVAL -> pop car

    public ArrayList<CarModel> cars = new ArrayList<>();

    public CarQueueModel(int x, int y, int angle, int floor) {
        super(x, y, angle, floor);
    }

    public boolean removeCar(CarModel car) {
        notifyViews();
        return cars.remove(car);
    }

}
