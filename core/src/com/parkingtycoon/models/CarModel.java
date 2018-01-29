package com.parkingtycoon.models;

import com.badlogic.gdx.graphics.Color;
import com.parkingtycoon.helpers.Random;

/**
 * This Class is responsible for storing all data that is known to a Car.
 */
public class CarModel extends PathFollowerModel {

    public long startTime, endTime, timer;

    public String license = "";
    public Color color;


    public CarModel() {
        license = "test_"+ Random.randomInt(99999); // TODO: generate realistic license plate String
        color = Random.randomColor();
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
