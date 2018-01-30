package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.Delegate;

/**
 * This Class is responsible for enabling Controllers to be called multiple times per frame depending on the speed of the simulation.
 */
public class SimulationController extends BaseController {

    public final static int REAL_TIME_UPDATES_PER_SECOND = 20;

    public boolean isSimulatorRunning = true;

    private Delegate<UpdateableController> updatables = new Delegate<>(false);
    private int updatesPerSecond = REAL_TIME_UPDATES_PER_SECOND;
    private long updates;
    private long deltaTime;
    private long prevTime;
    private boolean paused = false;
    private boolean pausedUpdate = false;
    private Delegate.Notifier<UpdateableController> notifier = UpdateableController::update;

    private int updatesSinceLastSecond = 0;
    private int millisTimer = 0;
    private long prevMillis = 0;

    public int realUpdatesPerSecond;

    public void startSimulation() {
        new Thread( () -> {
            while (isSimulatorRunning) update();
        }).start();
    }

    public void update() {
      
        if (paused && pausedUpdate)
            return;

        long time = System.currentTimeMillis();

        float timeStep = 1000 / (float) updatesPerSecond;
        deltaTime += Math.min(time - prevTime, 250);

        while (deltaTime >= timeStep) {

            pausedUpdate = true; // only pause if there has been a new render

            if (paused) break;

            updates++;
            updatesSinceLastSecond++;
            updatables.notifyObjects(notifier);

            millisTimer += time - prevMillis;
            prevMillis = time;

            if (millisTimer >= 1000) {

                realUpdatesPerSecond = updatesSinceLastSecond;
                millisTimer = 0;
                updatesSinceLastSecond = 0;

            }

            addCars();

            deltaTime -= timeStep;
        }

        prevTime = time;
    }

    public boolean registerUpdatable(UpdateableController updatable) {
        return updatables.register(updatable);
    }

    public boolean unregisterUpdatable(UpdateableController updatable) {
        return updatables.unregister(updatable);
    }


    public void setUpdatesPerSecond(int updatesPerSecond) {
        this.updatesPerSecond = updatesPerSecond;
    }

    public int getUpdatesPerSecond() {
        return paused ? 0 : updatesPerSecond;
    }

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

    private void addCars() { // todo: remove to an appropriate controller
        if (Math.random() > .97f) {
            CompositionRoot.getInstance().carsController.spawnCar();
        }
    }

    public long getUpdates() {
        return updates;
    }
}
