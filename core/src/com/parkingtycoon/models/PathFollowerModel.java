package com.parkingtycoon.models;

import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.helpers.interfaces.FloorDependable;
import com.parkingtycoon.helpers.pathfinding.PathFinder;

import java.util.List;

/**
 * This Class is responsible for storing all data that is necessary to follow a path.
 */
public abstract class PathFollowerModel extends BaseModel implements FloorDependable {

    public int currentNode;
    public int floor;
    public float speed = 0.1f; // in meters per update
    public Vector2 direction = new Vector2();
    public Vector2 position = new Vector2();
    public boolean pathSmoothing = true;

    public Goal goal;
    public List<PathFinder.Node> pathToElevator, pathToGoal;
    public boolean elevationDone, goingToElevator, goingToGoal;

    private List<PathFinder.Node> currentPath;
    private boolean disappear;
    private boolean onActiveFloor;

    /**
     * A goal describes where a PathFollower has to go to.
     * A goal has an arrived() and a failed() method.
     */
    public static class Goal {

        public final int floor, toX, toY, fromX, fromY;

        /**
         * Initializes the final variables of the goal
         *
         * @param floor Floor on which the goal is on
         * @param toX   Goal x
         * @param toY   Goal y
         * @param fromX From x
         * @param fromY From y
         */
        public Goal(int floor, int toX, int toY, int fromX, int fromY) {
            this.floor = floor;
            this.toX = toX;
            this.toY = toY;
            this.fromX = fromX;
            this.fromY = fromY;
        }

        /**
         * This method is called when the PathFollower successfully arrived at its goal.
         */
        public void arrived() {}

        /**
         * This method is called when the PathFollower failed to arrive at its goal.
         */
        public void failed() {}
    }

    /**
     * This method will change the position of the PathFollower according to its direction.
     */
    public void move() {

        position.add(direction);
        notifyViews();

    }

    /**
     * Returns the path the PathFollower is currently following
     * @return The path the PathFollower is currently following
     */
    public List<PathFinder.Node> getCurrentPath() {
        return currentPath;
    }

    /**
     * Sets the path the PathFollower will follow
     * @param currentPath The path the PathFollower will follow
     */
    public void setCurrentPath(List<PathFinder.Node> currentPath) {
        this.currentPath = currentPath;
        this.currentNode = 0;
        notifyViews();
    }

    @Override
    public boolean isOnActiveFloor() {
        return onActiveFloor;
    }

    @Override
    public void setOnActiveFloor(boolean onActiveFloor) {
        this.onActiveFloor = onActiveFloor;
        notifyViews();
    }

    public void setDisappeared() {
        disappear = true;
        notifyViews();      // now the views know that the object is no longer in the game
    }

    public boolean isDisappeared() {
        return disappear;
    }

}
