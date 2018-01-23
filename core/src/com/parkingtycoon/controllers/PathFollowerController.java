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
            if (f.path == null)
                continue;

            if (f.currentNode + 1 == f.path.size()) {
                f.path = null;  // arrived at goal
                continue;
            }

            PathFinder.Node next = f.path.get(f.currentNode + 1);

            float diffX = f.position.x - next.x;
            float diffY = f.position.y - next.y;
            float distanceToNext = (float) Math.sqrt(diffX * diffX + diffY * diffY);

            float velocity = f.velocity;

            if (distanceToNext < velocity) {    // arrived at next node
                velocity = distanceToNext;
                f.currentNode++;
            }

            f.direction.set(next.x - f.position.x, next.y - f.position.y).nor().scl(velocity);
            f.position.add(f.direction);

        }

    }

    public void sendTo(PathFollowerModel pathFollower, NavMap navMap, int x, int y) {
        pathFollower.path = PathFinder.calcPath(
                navMap,
                (int) pathFollower.position.x,      // from x
                (int) pathFollower.position.y,      // from y
                x,                                  // to x
                y                                   // to y
        );
        pathFollower.currentNode = 0;
    }

}
