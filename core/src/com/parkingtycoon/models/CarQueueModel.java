package com.parkingtycoon.models;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 * This Class is responsible for storing a Queue of Cars.
 */
public class CarQueueModel extends BuildingModel {

    public int popTimer; // when popTimer >= popInterval -> pop car
    public int popInterval;
    public int maxQueueLength = 6;
    public EnumSet<CarModel.CarType> carTypes;
    public ArrayList<CarModel> cars = new ArrayList<>();

    /**
     * A new CarQueue is built here.
     *
     * @param x        X-position of the queue
     * @param y        Y-position of the queue
     * @param angle    Angle of the queue
     * @param floor    Floor that the queue is standing on
     * @param carTypes Types of cars that this queue can process
     */
    public CarQueueModel(int x, int y, int angle, int floor, EnumSet<CarModel.CarType> carTypes) {
        super(x, y, angle, floor);
        this.carTypes = carTypes;
    }

    /**
     * Notify the views when removing a car from the queue
     * @param car Car to remove
     * @return    Whether car could be removed
     */
    public boolean removeCar(CarModel car) {
        notifyViews();
        return cars.remove(car);
    }

}
