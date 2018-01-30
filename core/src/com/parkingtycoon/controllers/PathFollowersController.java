package com.parkingtycoon.controllers;

import com.parkingtycoon.helpers.pathfinding.PathFinder;
import com.parkingtycoon.models.PathFollowerModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class is responsible for providing a basic classes that would like to use a Path
 */
public abstract class PathFollowersController<T extends PathFollowerModel> extends UpdateableController {

    public ArrayList<T> pathFollowers = new ArrayList<>();

    @Override
    public void update() {

        Iterator<T> iterator = pathFollowers.iterator();
        while (iterator.hasNext()) {

            T f = iterator.next();

            checkGoal(f);

            if (f.isDisappeared())
                iterator.remove();

            if (f.getCurrentPath() == null)
                f.direction.set(0, 0);

            float distanceTravelled = 0;
            while (distanceTravelled < f.speed && f.getCurrentPath() != null)
                distanceTravelled += travel(f, f.speed - distanceTravelled);

        }

    }

    private void checkGoal(T f) {

        if (f.goal == null)
            return;

        if (f.getCurrentPath() == null) {

            if (f.goingToElevator) {

                // todo: play elevator animation etc.
                f.floor = f.goal.floor;

                f.pathToElevator = null;
                f.goingToElevator = false;
                f.goingToGoal = true;
                f.setCurrentPath(f.pathToGoal);

            } else if (f.goingToGoal) {

                f.pathToGoal = null;
                f.goingToGoal = false;
                f.goal.arrived();

            } else {

                f.goingToElevator = f.pathToElevator != null;
                f.goingToGoal = !f.goingToElevator;
                f.setCurrentPath(f.goingToElevator ? f.pathToElevator : f.pathToGoal);

            }
        }
    }

    private float travel(PathFollowerModel f, float maxDistance) {

        if (f.currentNode + 1 >= f.getCurrentPath().size()) {
            f.setCurrentPath(null);
            return 0;
        }

        PathFinder.Node next = f.getCurrentPath().get(f.currentNode + 1);

        if (f.pathSmoothing && f.currentNode + 2 < f.getCurrentPath().size()) {

            PathFinder.Node afterNext = f.getCurrentPath().get(f.currentNode + 2);

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

    public void onTerrainChange(int floor, Boolean[][] tilesChanged) {
        pathFollowers:
        for (T f : pathFollowers) {

            if (f.goal == null)
                continue;

            for (int i = 0; i < 2; i++) {

                if (floor != (i == 0 ? f.floor : f.goal.floor)) // the terrain did not change on this floor
                    continue;

                List<PathFinder.Node> path = i == 0 ? f.pathToElevator : f.pathToGoal;

                if (path == null)
                    continue;

                for (PathFinder.Node n : path) {

                    if (tilesChanged[n.x] != null && Boolean.TRUE.equals(tilesChanged[n.x][n.y])) {
                        PathFollowerModel.Goal failedGoal = f.goal;
                        cancelGoal(f);
                        failedGoal.failed();
                        continue pathFollowers;
                    }
                }
            }
        }
    }

    public boolean setGoal(T pathFollower, PathFollowerModel.Goal goal) {

        List<PathFinder.Node> pathToElevator = null, pathToGoal;

        int fromX = goal.fromX, fromY = goal.fromY;

        if (pathFollower.floor != goal.floor) {
            pathToElevator = getPathToElevator(pathFollower.floor, fromX, fromY);

            if (pathToElevator == null)
                return false;

            PathFinder.Node lastNode = pathToElevator.get(pathToElevator.size() - 1);
            fromX = lastNode.x;
            fromY = lastNode.y;
        }

        pathToGoal = getPath(goal.floor, fromX, fromY, goal.toX, goal.toY);

        if (pathToGoal == null)
            return false;

        pathFollower.pathToElevator = pathToElevator;
        pathFollower.goingToElevator = false;
        pathFollower.pathToGoal = pathToGoal;
        pathFollower.goingToGoal = false;
        pathFollower.goal = goal;

        return true;
    }

    public void cancelGoal(T f) {
        f.goingToElevator = false;
        f.goingToGoal = false;
        f.pathToElevator = null;
        f.pathToGoal = null;
        f.setCurrentPath(null);
        f.goal = null;
    }

    protected abstract List<PathFinder.Node> getPathToElevator(int floor, int fromX, int fromY);

    protected abstract List<PathFinder.Node> getPath(int floor, int fromX, int fromY, int toX, int toY);

}
