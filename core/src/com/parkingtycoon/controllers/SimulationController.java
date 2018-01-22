package com.parkingtycoon.controllers;

import com.badlogic.gdx.Gdx;
import com.parkingtycoon.helpers.UpdateableController;

import java.util.ArrayList;

public class SimulationController extends BaseController {

    public final static int REAL_TIME_UPDATES_PER_SECOND = 20;

    private ArrayList<UpdateableController> updatables = new ArrayList<>();
    private int updatesPerSecond = REAL_TIME_UPDATES_PER_SECOND;
    private long updates = 0;
    private float deltaTime = 0;
    private boolean paused = false;
    private boolean pausedUpdate = false;


    public void update() {

        if (paused && pausedUpdate)
            return;

        float timeStep = 1 / (float) updatesPerSecond;
        deltaTime += Math.min(Gdx.graphics.getDeltaTime(), .25f);

        while (deltaTime >= timeStep) {

            updates++;
            pausedUpdate = true; // only pause if there has been a new render

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
        return paused ? 0 : updatesPerSecond;
    }


    // PAUSED
    public void pause() {
        paused = true;
        pausedUpdate = false;
    }

    public void resume() {
        paused = false;
        pausedUpdate = false;
    }

    public void togglePaused() {
        paused = !paused;
        pausedUpdate = false;
    }
}
