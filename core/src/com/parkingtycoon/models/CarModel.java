package com.parkingtycoon.models;

import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.helpers.AABB;

import java.util.Random;

/**
 * Stores all data that is known to a CAR. This class is managed by CarController;
 */
public class CarModel extends PathFollowerModel {

    public float brake = 0;
    public long startTime, endTime, timer;
    public AABB aabb = new AABB(new Vector2(), new Vector2(.15f, .15f));
    public CarModel waitingOn;
    public boolean waitingInQueue, parked;

    private String license = "";

    /**
     * Standard constructor
     */
    public CarModel() {
        speed = .2f;
        Random random = new Random();
        license = "test_"+random.nextInt(); // TODO: generate realistic license plate String
    }

    /**
     * Optional constructor where you need to give me a license plate string
     * @param license a string that is used as identifying the car
     */
    public CarModel(String license) {
        this.license = license;
    }

    public String getLicense() {
        return license;
    }

    @Override
    public void move() {
        position.add(direction.x * (1 - brake), direction.y * (1 - brake));
        notifyViews();
    }
}
