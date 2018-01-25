package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.models.CarModel;

/**
 * This class is responsible for providing a blueprint for any kind of Queue.
 */
public abstract class CarQueuesController extends UpdateableController {

    public CarQueuesController() {
        CompositionRoot.getInstance().simulationController.registerUpdatable(this);
    }

    public abstract boolean addToQueue(CarModel car);

}
