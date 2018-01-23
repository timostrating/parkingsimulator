package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.Random;
import com.parkingtycoon.helpers.UpdateableController;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.views.CarView;

import java.util.ArrayList;

public class SimulationController extends BaseController {

    public final static int REAL_TIME_UPDATES_PER_SECOND = 20;

    public boolean isSimulatorRunning = true;

    private ArrayList<UpdateableController> updatables = new ArrayList<>();
    private int updatesPerSecond = REAL_TIME_UPDATES_PER_SECOND;
    private long updates;
    private float deltaTime;
    private long prevTime;

    public void startSimulation() {
        new Thread( () -> {
            while (isSimulatorRunning) update();
        }).start();
    }

    public void update() {

        long time = System.currentTimeMillis();

        float timeStep = 1000 / (float) updatesPerSecond;
        deltaTime += Math.min(time - prevTime, 250);

        while (deltaTime >= timeStep) {


            updates++;

            for (UpdateableController u : updatables)
                u.update();

            addCars();

            deltaTime -= timeStep;
        }

        prevTime = time;
    }

    public boolean registerUpdatable(UpdateableController updatable) {
        return updatables.add(updatable);
    }

    public boolean unregisterUpdatable(UpdateableController updatable) {
        return updatables.remove(updatable);
    }

    public int getUpdatesPerSecond() {
//        Logger.info("ups" + updatesPerSecond);
        return updatesPerSecond;
    }

    private void addCars() {
        if (Math.random() > .96f || true) {
            CarModel car = CompositionRoot.getInstance().carsController.createCar();
            car.startTime = updates;
            car.endTime = updates + Random.randomInt(50, 200);
            car.registerView(new CarView());
            CompositionRoot.getInstance().entrancesController.addToQueue(car);
        }
    }

}
