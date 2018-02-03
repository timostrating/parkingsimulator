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

    public float getDoorsTimer() {
        return doorsTimer;
    }

    public void setDoorsTimer(float doorsTimer) {
        this.doorsTimer = doorsTimer;
        notifyViews();
    }
}
