package com.parkingtycoon.models;

import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.helpers.pathfinding.PathFinder;

import java.util.List;

/**
 * This Class is responsible for storing all data that is necessary to follow a path.
 */
public abstract class PathFollowerModel extends BaseModel {

    public int currentNode;
    public int floor;
    public float speed = 0.1f; // in meters per update
    public Vector2 direction = new Vector2();
    public Vector2 position = new Vector2();
    public boolean pathSmoothing = true;

    private List<PathFinder.Node> path;

    public void move() {

        position.add(direction);
        notifyViews();

    }

    public List<PathFinder.Node> getPath() {
        return path;
    }

    public void setPath(List<PathFinder.Node> path) {
        this.path = path;
        notifyViews();
    }
}
