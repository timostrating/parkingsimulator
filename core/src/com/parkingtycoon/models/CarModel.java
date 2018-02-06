package com.parkingtycoon.models;

import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.helpers.AABB;
import com.parkingtycoon.helpers.LicenceGenerator;

/**
 * This Class is responsible for storing all data that is known to a Car.
 */
public class CarModel extends PathFollowerModel {

    /**
     * There are different types of cars.
     */
    public enum CarType {
        AD_HOC,
        RESERVED,
        VIP
    }

    public CarType carType;
    public float brake = 0;
    public long startTime, endTime, timer;
    public int reservationTimer;
    public int waitingTooLongTimer;
    public AABB aabb = new AABB(new Vector2(), new Vector2(.15f, .15f));
    public CarModel waitingOn;
    public boolean waitingInQueue, firstInQueue, parked, alwaysPrior, claimedReservedPlace;
    public CarQueueModel queue;

    private String license;

    /**
     * Construct a Car with a given type
     * @param type Type of this car
     */
    public CarModel(CarType type) {
        carType = type;
        speed = .2f;
        license = LicenceGenerator.getRandomLicencePlate();
    }

    /**
     * Returns the licence plate of the car
     * @return The licence plate of the car
     */
    public String getLicense() {
        return license;
    }

    /**
     * This method will change the position of the car according to its direction
     */
    @Override
    public void move() {
        position.add(direction.x * (1 - brake), direction.y * (1 - brake));
        notifyViews();
    }

}
