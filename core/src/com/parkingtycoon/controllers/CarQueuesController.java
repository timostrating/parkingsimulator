package com.parkingtycoon.controllers;

import com.parkingtycoon.models.CarModel;

/**
 * This class is responsible for providing a blueprint for any kind of Queue.
 */
public abstract class CarQueuesController extends UpdateableController {

    public abstract boolean addToQueue(CarModel car);

}
