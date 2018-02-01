package com.parkingtycoon.models;

import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.helpers.AABB;
import com.parkingtycoon.helpers.LicenceGenerator;

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

    private CarType carType = CarType.AD_HOC;

    private String license;

    public CarModel() {
        speed = .2f;
        license = LicenceGenerator.getRandomLicencePlate();
    }

    public String getLicense() {
        return license;
    }

    @Override
    public void move() {
        position.add(direction.x * (1 - brake), direction.y * (1 - brake));
        notifyViews();
    }

    public enum CarType {
        AD_HOC,
        RESERVED
    }

}
