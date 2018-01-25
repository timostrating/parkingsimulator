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
            while (distanceTravelled < f.velocity) {

                if (f.path == null)
                    break;

                if (f.currentNode + 1 >= f.path.size()) {
                    f.path = null;  // arrived at goal
                    continue;
                }

                PathFinder.Node next = f.path.get(f.currentNode + 1);

                float diffX = f.position.x - next.actualX;
                float diffY = f.position.y - next.actualY;
                float distanceToNext = (float) Math.sqrt(diffX * diffX + diffY * diffY);

                if (f.pathSmoothing && f.currentNode + 2 < f.path.size()) {
                    float influence = Math.max(0,  1 - distanceToNext);

                    PathFinder.Node afterNext = f.path.get(f.currentNode + 2);

                    next.actualX = next.actualX * (1 - influence) + afterNext.actualX * influence;
                    next.actualY = next.actualY * (1 - influence) + afterNext.actualY * influence;
                }

                float velocity = f.velocity;

                if (distanceToNext < velocity) {    // arrived at next node
                    velocity = distanceToNext;
                    f.currentNode++;
                }

                f.direction.set(next.actualX - f.position.x, next.actualY - f.position.y).nor().scl(velocity);
                f.position.add(f.direction);

                distanceTravelled += f.direction.len();

            }
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
        pathFollower.currentNode = 1;
    }

}
