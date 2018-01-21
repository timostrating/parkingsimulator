package com.parkingtycoon.controllers;

import com.badlogic.gdx.Gdx;
import com.parkingtycoon.helpers.UpdateableController;

import java.util.ArrayList;

public class SimulationController extends BaseController {

    public final static int REAL_TIME_UPDATES_PER_SECOND = 20;

    private ArrayList<UpdateableController> updatables = new ArrayList<>();
    private int updatesPerSecond = REAL_TIME_UPDATES_PER_SECOND;
    private long updates;
    private float deltaTime;



    public void update() {

        float timeStep = 1 / (float) updatesPerSecond;
        deltaTime += Math.min(Gdx.graphics.getDeltaTime(), .25f);

        while (deltaTime >= timeStep) {

            updates++;

            for (UpdateableController u : updatables)
                u.update();

            deltaTime -= timeStep;
        }
    }

    public boolean registerUpdatable(UpdateableController updatable) {
        return updatables.add(updatable);
    }

    public boolean unregisterUpdatable(UpdateableController updatable) {
        return updatables.remove(updatable);
    }

    public int getUpdatesPerSecond() {
        return updatesPerSecond;
    }
}
