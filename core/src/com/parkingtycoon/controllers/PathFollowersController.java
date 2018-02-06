package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.pathfinding.PathFinder;
import com.parkingtycoon.models.PathFollowerModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class is responsible for making PathFollowers follow a path, using goals and elevators.
 *
 * @author Hilko Janssen
 */
public abstract class PathFollowersController<T extends PathFollowerModel> extends UpdateableController {

    public ArrayList<T> pathFollowers = new ArrayList<>();

    protected FloorsController floorsController = CompositionRoot.getInstance().floorsController;

    /**
     * This update method will check for each pathFollower if it has arrived on its destination
     * Otherwise it will make the pathFollowers travel.
     */
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

    /**
     * This method checks if a pathFollower has arrived at its goal.
     *
     * @param f The regarding PathFollower
     */
    private void checkGoal(T f) {

        if (f.goal == null)
            return;

        if (f.getCurrentPath() == null) {

            if (f.goingToElevator) {

                if (f.elevationDone) {
                    f.elevationDone = false;
                    f.pathToElevator = null;
                    f.goingToElevator = false;
                    f.goingToGoal = true;
                    f.setCurrentPath(f.pathToGoal);
                }

            } else if (f.goingToGoal) {

                f.pathToGoal = null;
                f.goingToGoal = false;
                f.goal.arrived();
                f.goal = null;

            } else {

                f.goingToElevator = f.pathToElevator != null;
                f.goingToGoal = !f.goingToElevator;
                f.setCurrentPath(f.goingToElevator ? f.pathToElevator : f.pathToGoal);

            }
        }
    }

    /**
     * This method will make a pathFollower travel
     *
     * @param f           The regarding PathFollower
     * @param maxDistance The maximal distance the pathFollower is allowed to travel
     * @return            The distance the pathFollower has travelled
     */
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

    /**
     * This method checks if changes to the floor layout have interrupted a path of a PathFollower
     *
     * @param floorIndex   The floor the changes were made
     * @param tilesChanged Which tiles are changed?
     */
    public void onTerrainChange(int floorIndex, Boolean[][] tilesChanged) {
        pathFollowers:
        for (T f : pathFollowers) {

            if (f.goal == null) {
                int x = (int) f.position.x;
                int y = (int) f.position.y;

                if (tilesChanged[x] != null && Boolean.TRUE.equals(tilesChanged[x][y]))
                    onIdlesFloorChanged(f);
                continue;
            }

            for (int i = 0; i < 2; i++) {

                if (floorIndex != (i == 0 ? f.floor : f.goal.floor)) // the terrain did not change on this floor
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

    /**
     * This method is called when the player switches between floors.
     * This is to notify every PathFollower.
     *
     * @param floor The new floorIndex
     */
    public void onFloorSwitch(int floor) {
        for (T f : pathFollowers)
            f.setOnActiveFloor(f.floor == floor);
    }

    /**
     * This method will try to send a PathFollower to a new Goal.
     *
     * @param pathFollower The regarding PathFollower
     * @param goal         The new Goal
     * @return             Whether the goal is accessible
     */
    public boolean setGoal(T pathFollower, PathFollowerModel.Goal goal) {

        List<PathFinder.Node> pathToElevator = null, pathToGoal;

        int fromX = goal.fromX, fromY = goal.fromY;

        if (pathFollower.floor != goal.floor) {
            pathToElevator = getPathToElevator(pathFollower, fromX, fromY);

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
        pathFollower.elevationDone = false;
        pathFollower.pathToGoal = pathToGoal;
        pathFollower.goingToGoal = false;
        pathFollower.goal = goal;

        return true;
    }

    /**
     * This method cancels the journey to a goal.
     * @param f The regarding PathFollower
     */
    public void cancelGoal(T f) {
        f.goingToElevator = false;
        f.goingToGoal = false;
        f.pathToElevator = null;
        f.pathToGoal = null;
        f.setCurrentPath(null);
        f.goal = null;
    }

    /**
     * This method should try to find a path to a elevator
     *
     * @param pathFollower  The pathFollower that has to go to an elevator
     * @param fromX         From where to calculate the path
     * @param fromY         From where to calculate the path
     * @return              Whether or not a path was found to an elevator
     */
    protected abstract List<PathFinder.Node> getPathToElevator(T pathFollower, int fromX, int fromY);

    /**
     * This method should ask the PathFinder for a path from point A to B
     *
     * @param floor On which floor
     * @param fromX From where
     * @param fromY From where
     * @param toX   To where
     * @param toY   To where
     * @return      The found path
     */
    protected abstract List<PathFinder.Node> getPath(int floor, int fromX, int fromY, int toX, int toY);

    /**
     * This method is called when the floor underneath an idle pathFollower has changed.
     *
     * @param pathFollower The regarding PathFollower
     */
    protected abstract void onIdlesFloorChanged(T pathFollower);

}
