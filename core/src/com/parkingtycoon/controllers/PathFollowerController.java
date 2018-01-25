package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.models.PathFollowerModel;

import java.util.ArrayList;

/**
 * This class is responsible for providing a basic classes that would like to use a Path
 */
public class PathFollowerController<T extends PathFollowerModel> extends UpdateableController {

    ArrayList<T> pathFollowers = new ArrayList<>();

    public PathFollowerController() {
        CompositionRoot.getInstance().simulationController.registerUpdatable(this);
    }

    @Override
    public void update() {

        for (T pathFollower : pathFollowers) {
            if (pathFollower.path == null)
                continue;

            // follow the path
        }

    }

}
