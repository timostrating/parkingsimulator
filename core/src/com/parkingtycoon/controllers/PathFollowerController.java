package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.Logger;
import com.parkingtycoon.helpers.UpdateableController;
import com.parkingtycoon.models.PathFollowerModel;

import java.util.ArrayList;

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

            Logger.info(pathFollower.path);

            // follow the path
        }

    }

}
