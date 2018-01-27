package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.pathfinding.NavMap;
import com.parkingtycoon.helpers.pathfinding.PathFinder;
import com.parkingtycoon.models.PathFollowerModel;

import java.util.ArrayList;

/**
 * This class is responsible for providing a basic classes that would like to use a Path
 */
public class PathFollowerController<T extends PathFollowerModel> extends UpdateableController {

    ArrayList<T> pathFollowers = new ArrayList<>();


    public PathFollowerController() {
        CompositionRoot.getInstance().simulationController.registerUpdatable(this);
    }

    @Override
    public void update() {

        for (T f : pathFollowers) {

            float distanceTravelled = 0;
            while (distanceTravelled < f.speed && f.getPath() != null)
                distanceTravelled += travel(f, f.speed - distanceTravelled);

            if (f.getPath() == null)
                f.direction.set(0, 0);
        }

    }

    private float travel(PathFollowerModel f, float maxDistance) {

        if (f.currentNode + 1 >= f.getPath().size()) {
            f.setPath(null);
            return 0;
    }

        PathFinder.Node next = f.getPath().get(f.currentNode + 1);

        if (f.pathSmoothing && f.currentNode + 2 < f.getPath().size()) {

            PathFinder.Node afterNext = f.getPath().get(f.currentNode + 2);

            next.actualX = (next.actualX * 10 + afterNext.actualX) / 11f;
            next.actualY = (next.actualY * 10 + afterNext.actualY) / 11f;
        }

        float diffX = f.position.x - next.actualX;
        float diffY = f.position.y - next.actualY;
        float distanceToNext = (float) Math.sqrt(diffX * diffX + diffY * diffY);

        float travelDistance = Math.min(maxDistance, f.speed);

        if (travelDistance >= distanceToNext) {    // arrived at next node
            travelDistance = distanceToNext;
            f.currentNode++;
        }

        f.direction.set(next.actualX - f.position.x, next.actualY - f.position.y).nor().scl(travelDistance);
        f.move();

        return travelDistance;
    }

    public void sendTo(PathFollowerModel pathFollower, NavMap navMap, int x, int y) {
        pathFollower.setPath(PathFinder.calcPath(
                navMap,
                (int) pathFollower.position.x,      // from x
                (int) pathFollower.position.y,      // from y
                x,                                  // to x
                y                                   // to y
        ));
        pathFollower.currentNode = 0;
    }

}
