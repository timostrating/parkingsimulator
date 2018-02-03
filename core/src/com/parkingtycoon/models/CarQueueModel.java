package com.parkingtycoon.models;

import java.util.ArrayList;

/**
 * This Class is responsible for storing a Queue of Cars.
 */
public class CarQueueModel extends BuildingModel {

    public int popTimer; // when popTimer == POP_INTERVAL -> pop car
    public final boolean normal, vip;

    public ArrayList<CarModel> cars = new ArrayList<>();

    public CarQueueModel(int x, int y, int angle, int floor, boolean normal, boolean vip) {
        super(x, y, angle, floor);
        this.normal = normal;
        this.vip = vip;
    }

    public boolean removeCar(CarModel car) {
        notifyViews();
        return cars.remove(car);
    }

}
