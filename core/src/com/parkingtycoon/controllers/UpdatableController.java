package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.interfaces.Updatable;

/**
 * This Class provides help to the Update loop to force only Controllers to be registrable.
 *
 * This class extends the base controller and implements the Updatable interface as extra functionality.
 *
 * @author Timo Strating
 */
public abstract class UpdatableController extends BaseController implements Updatable {

    /**
     * The standard constructor
     */
    public UpdatableController() {
        CompositionRoot.getInstance().simulationController.registerUpdatable(this);
    }
}
