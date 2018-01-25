package com.parkingtycoon.controllers;

import com.parkingtycoon.helpers.interfaces.Updatable;

/**
 * TODO: start using it
 */
public class ChartsController extends BaseController implements Updatable {

    public ChartsController() {

//        CompositionRoot.getInstance().applicationController.registerUpdatable(this);

    }

    @Override
    public void update() {
        System.out.println("hhhh");
    }
}
