package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.interfaces.Updatable;

/**
 * This Class provides help to the Update loop to force only Controllers to be registrable.
 */
public abstract class UpdateableController extends BaseController implements Updatable {

    public UpdateableController() {
        CompositionRoot.getInstance().simulationController.registerUpdatable(this);
    }
}
