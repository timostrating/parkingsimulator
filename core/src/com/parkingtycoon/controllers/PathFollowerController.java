package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.UpdateableController;
import com.parkingtycoon.models.PathFollowerModel;
import com.parkingtycoon.pathfinding.NavMap;
import com.parkingtycoon.pathfinding.PathFinder;

import java.util.ArrayList;

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

            next.actualX = (next.actualX * 3 + afterNext.actualX) / 4f;
            next.actualY = (next.actualY * 3 + afterNext.actualY) / 4f;
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
