package com.parkingtycoon.models;

import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.helpers.interfaces.FloorDependable;
import com.parkingtycoon.helpers.pathfinding.PathFinder;

import java.util.List;

/**
 * This Class is responsible for storing all data that is necessary to follow a currentPath.
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

    public static class Goal {

        public final int floor, toX, toY, fromX, fromY;

        public Goal(int floor, int toX, int toY, int fromX, int fromY) {
            this.floor = floor;
            this.toX = toX;
            this.toY = toY;
            this.fromX = fromX;
            this.fromY = fromY;
        }

        public void arrived() {}
        public void failed() {}
    }

    public void move() {

        position.add(direction);
        notifyViews();

    }

    public List<PathFinder.Node> getCurrentPath() {
        return currentPath;
    }

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
