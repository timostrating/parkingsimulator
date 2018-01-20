package com.parkingtycoon.controllers;

import com.badlogic.gdx.Gdx;
import com.parkingtycoon.interfaces.Updatable;
import com.parkingtycoon.models.SimulationModel;

public class SimulationController extends BaseController {

    private SimulationModel model = new SimulationModel();

    public boolean registerUpdatable(Updatable updatable) {
        return model.updatables.add(updatable);
    }

    public boolean unregisterUpdatable(Updatable updatable) {
        return model.updatables.remove(updatable);
    }

    public void update() {

        float timeStep = 1 / (float) model.updatesPerSecond;
        model.deltaTime += Math.min(Gdx.graphics.getDeltaTime(), .25f);

        while (model.deltaTime >= timeStep) {

            model.updates++;

            for (Updatable u : model.updatables)
                u.update();

            model.deltaTime -= timeStep;
        }

    }

    public SimulationModel getModel() {
        return model;
    }
}
