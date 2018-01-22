package com.parkingtycoon.controllers;

import com.badlogic.gdx.Gdx;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.Random;
import com.parkingtycoon.helpers.UpdateableController;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.views.CarView;

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

            addCars();

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

    private void addCars() {
        if (Math.random() > .96f) {
            CarModel car = new CarModel();
            car.startTime = updates;
            car.endTime = updates + Random.randomInt(50, 200);
            car.registerView(new CarView());
            CompositionRoot.getInstance().entrancesController.addToQueue(car);
        }
    }

}
