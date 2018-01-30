package com.parkingtycoon.models;

import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.helpers.AABB;
import com.badlogic.gdx.graphics.Color;
import com.parkingtycoon.helpers.LicenceGenerator;
import com.parkingtycoon.helpers.Random;

/**
 * This Class is responsible for storing all data that is known to a Car.
 */
public class CarModel extends PathFollowerModel {

    public float brake = 0;
    public long startTime, endTime, timer;
    public AABB aabb = new AABB(new Vector2(), new Vector2(.15f, .15f));
    public CarModel waitingOn;
    public boolean waitingInQueue, firstInQueue, parked;
    public CarQueueModel queue;

    public String license = "";
    public Color color;

    public CarModel() {
        speed = .2f;
        license = LicenceGenerator.getRandomLicencePlate();
        color = Random.randomColor();
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
