package com.parkingtycoon.models;

import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.helpers.AABB;
import com.parkingtycoon.helpers.LicenceGenerator;

/**
 * This Class is responsible for storing all data that is known to a Car.
 */
public class CarModel extends PathFollowerModel {
  
    public enum CarType {
        AD_HOC,
        RESERVED
    }

    public CarType carType = CarType.AD_HOC;
    public float brake = 0;
    public long startTime, endTime, timer;
    public AABB aabb = new AABB(new Vector2(), new Vector2(.15f, .15f));
    public CarModel waitingOn;
    public boolean waitingInQueue, firstInQueue, parked, vip, alwaysPrior;
    public CarQueueModel queue;

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

}
