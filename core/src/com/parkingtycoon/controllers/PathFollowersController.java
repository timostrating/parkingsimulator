package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.pathfinding.NavMap;
import com.parkingtycoon.helpers.pathfinding.PathFinder;
import com.parkingtycoon.models.PathFollowerModel;

import java.util.ArrayList;

/**
 * This class is responsible for providing a basic classes that would like to use a Path
 */
public abstract class PathFollowersController<T extends PathFollowerModel> extends UpdateableController {

    public ArrayList<T> pathFollowers = new ArrayList<>();

    public PathFollowersController() {
        CompositionRoot.getInstance().simulationController.registerUpdatable(this);
    }

    @Override
    public void update() {

        for (T f : pathFollowers) {

            if (f.getPath() == null)
                f.direction.set(0, 0);

            PathFollowerModel.Goal goal = f.getGoal();

            if (goal == null)
                continue;

            boolean arrived = goal.toX == (int) f.position.x && goal.toY == (int) f.position.y;

            if (arrived) {
                goal.arrived();
                f.setGoal(null);
                continue;

            } else if (f.getPath() == null) {

                if (f.floor != goal.floor)
                    f.floor = goal.floor; // todo: use elevator to switch floor.

                sendTo(f, goal.toX, goal.toY);

            }

            float distanceTravelled = 0;
            while (distanceTravelled < f.speed && f.getPath() != null)
                distanceTravelled += travel(f, f.speed - distanceTravelled);

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

    protected boolean sendTo(T pathFollower, int x, int y) {
        pathFollower.setPath(PathFinder.calcPath(
                getNavMap(pathFollower),            // The NavigationMap that is used to find a path
                (int) pathFollower.position.x,      // from x
                (int) pathFollower.position.y,      // from y
                x,                                  // to x
                y                                   // to y
        ));
        pathFollower.currentNode = 0;
        return pathFollower.getPath() != null;
    }

    protected abstract NavMap getNavMap(T pathFollower);

}
