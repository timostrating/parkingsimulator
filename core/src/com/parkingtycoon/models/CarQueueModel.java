package com.parkingtycoon.models;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 * This Class is responsible for storing a Queue of Cars.
 */
public class CarQueueModel extends BuildingModel {

    public int popTimer; // when popTimer == POP_INTERVAL -> pop car
    public EnumSet<CarModel.CarType> carTypes;

    public ArrayList<CarModel> cars = new ArrayList<>();

    public CarQueueModel(int x, int y, int angle, int floor, EnumSet<CarModel.CarType> carTypes) {
        super(x, y, angle, floor);
        this.carTypes = carTypes;
    }

    public boolean removeCar(CarModel car) {
        notifyViews();
        return cars.remove(car);
    }

}
