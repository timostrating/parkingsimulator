package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.UpdateableController;
import com.parkingtycoon.models.CarModel;

public abstract class CarQueuesController extends UpdateableController {

    public CarQueuesController() {
        CompositionRoot.getInstance().simulationController.registerUpdatable(this);
    }

    public abstract boolean addToQueue(CarModel car);

}
