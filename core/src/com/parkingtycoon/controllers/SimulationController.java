package com.parkingtycoon.controllers;

import com.parkingtycoon.helpers.Delegate;

/**
 * This Class is responsible for enabling Controllers to be called multiple times per frame depending on the speed of the simulation.
 *
 * @author GGG
 */
public class SimulationController extends BaseController {

    public final static int REAL_TIME_UPDATES_PER_SECOND = 20;

    public boolean isSimulatorRunning = true;

    private Delegate<UpdatableController> updatables = new Delegate<>(false);
    private int updatesPerSecond = REAL_TIME_UPDATES_PER_SECOND;
    private long updates;
    private long deltaTime;
    private long prevTime;
    private boolean paused = false;
    private boolean pausedUpdate = false;
    private Delegate.Notifier<UpdatableController> notifier = UpdatableController::update;

    private int updatesSinceLastSecond = 0;
    private int millisTimer = 0;
    private long prevMillis = 0;

    public int realUpdatesPerSecond;


    /**
     * Start a new thread that starts calling update().
     */
    public void startSimulation() {
        new Thread( () -> {
            while (isSimulatorRunning) update();
        }).start();
    }

    /**
     * We try to update the screen as many times as possible as long as it is less than the maximum amount of updates per second.
     *
     * This is the old Tick.
     */
    public void update() {
      
        if (paused && pausedUpdate)
            return;

        long time = System.currentTimeMillis();

        float timeStep = 1000 / (float) updatesPerSecond;
        deltaTime += Math.min(time - prevTime, 250);

        while (deltaTime >= timeStep) {

            pausedUpdate = true; // only pause if there has been a new render

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
            deltaTime -= timeStep;

            if (paused) break;
        }

        prevTime = time;
    }

    /**
     * Register a Controller as a controller that would like to we updated.
     *
     * @param updatable the controller that would like to register.
     */
    public void registerUpdatable(UpdatableController updatable) {
        updatables.register(updatable);
    }

    /**
     * UnRegister a Controller as a controller that would like to we updated.
     *
     * @param updatable the controller that would like to remove itself from the list of Updatables.
     */
    public void unregisterUpdatable(UpdatableController updatable) {
        updatables.unregister(updatable);
    }

    /**
     * SETTER set the desired updates per second.
     *
     * @param updatesPerSecond the amount of updates you would like to hit.
     */
    public void setUpdatesPerSecond(int updatesPerSecond) {
        this.updatesPerSecond = updatesPerSecond;
    }

    /**
     * GETTER get the desired updates per second.
     */
    public int getUpdatesPerSecond() {
        return paused ? 0 : updatesPerSecond;
    }

    /**
     * Pause the simulation.
     */
    public void pause() {
        paused = true;
        pausedUpdate = false;
    }

    /**
     * resume the simulation.
     */
    public void resume() {
        paused = false;
        pausedUpdate = false;
    }

    /**
     * toggle the simulation on and off.
     */
    public void togglePaused() {
        paused = !paused;
        pausedUpdate = false;
    }

    /**
     * GETTER get the amount of updates until now.
     *
     * @return the counter that counts the amount of updates that have passed.
     */
    public long getUpdates() {
        return updates;
    }
}
