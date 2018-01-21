package com.parkingtycoon.controllers;

import com.parkingtycoon.interfaces.IUpdatable;

/**
 * Created by Hilko on 17-1-2018.
 */
public class ChartsController extends BaseController implements IUpdatable {

    public ChartsController() {

//        CompositionRoot.getInstance().applicationController.registerUpdatable(this);

    }

    @Override
    public void update() {
        System.out.println("hhhh");
    }
}
