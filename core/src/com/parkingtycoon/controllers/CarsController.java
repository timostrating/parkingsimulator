package com.parkingtycoon.controllers;

import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.helpers.Logger;
import com.parkingtycoon.helpers.pathfinding.NavMap;
import com.parkingtycoon.helpers.pathfinding.PathFinder;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.PathFollowerModel;

import java.util.ArrayList;


/**
 * This class is responsible for controlling the Cars in the simulation.
 */
public class CarsController extends PathFollowerController<CarModel> {

    @Override
    public void update() {
        super.update();

        for (CarModel car : pathFollowers)  // TODO: a controller should no be able to directly call the notifyViews function
            car.notifyViews();
    }

    public CarModel createCar() {

        CarModel car = new CarModel();
        pathFollowers.add(car);
        return car;

    }

    @Override
    public void sendTo(PathFollowerModel pathFollower, NavMap navMap, int x, int y) {
        Logger.info("hoi override");
        super.sendTo(pathFollower, navMap, x, y);

        if (pathFollower.path != null) {


            // move the nodes so that the cars drive on the right of the road

            PathFinder.Node prevNode = null;
            for (PathFinder.Node n : pathFollower.path) {
                n.actualX += .5f;
                n.actualY += .5f;
                if (prevNode != null) {

                    Vector2 direction = new Vector2(n.actualX - prevNode.actualX, n.actualY - prevNode.actualY);
                    direction.rotate90(1);
                    direction.nor().scl(.25f);
                    n.actualX += direction.x;
                    n.actualY += direction.y;

                }

                prevNode = n;
            }

        }
    }

    public ArrayList<CarModel> getCarModels() {
        return pathFollowers;
    }

}
