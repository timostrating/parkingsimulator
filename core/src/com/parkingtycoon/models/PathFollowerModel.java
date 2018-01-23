package com.parkingtycoon.models;

import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.pathfinding.PathFinder;

import java.util.List;

public abstract class PathFollowerModel extends BaseModel {

    public Vector2 position = new Vector2();
    public List<PathFinder.Node> path;
    public int currentNode;
    public float velocity = 0.1f; // in meters per update
    public Vector2 direction = new Vector2();

}
