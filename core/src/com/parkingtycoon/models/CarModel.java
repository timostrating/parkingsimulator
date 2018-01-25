package com.parkingtycoon.models;

import java.util.Random;

/**
 * Stores all data that is known to a CAR. This class is managed by CarController;
 */
public class CarModel extends PathFollowerModel {

    public long startTime, endTime, timer;

    private String license = "";

    /**
     * Standard constructor
     */
    public CarModel() {
        velocity = .2f;
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
  
}
