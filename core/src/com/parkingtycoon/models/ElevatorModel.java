package com.parkingtycoon.models;

import java.util.ArrayList;

public class ElevatorModel extends BuildingModel {

    public ArrayList<CarModel> cars = new ArrayList<>();
    public CarModel currentlyElevating;
    public int timeLeft;
    public int currentFloor;

    private float doorsTimer;

    public ElevatorModel(int x, int y, int angle) {
        super(x, y, angle, 0, true);
    }

    /**
     * Returns how far the doors are opened (range 0-1)
     * @return How far are the doors opened (range 0-1)
     */
    public float getDoorsTimer() {
        return doorsTimer;
    }

    /**
     * Set how far the doors are opened
     * @param doorsTimer How far the doors are opened (range 0-1)
     */
    public void setDoorsTimer(float doorsTimer) {
        this.doorsTimer = doorsTimer;
        notifyViews();
    }
}
