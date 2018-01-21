package com.parkingtycoon.controllers;

import com.parkingtycoon.models.FloorModel;
import com.parkingtycoon.views.FloorsView;

import java.util.ArrayList;

/**
 * Created by Sneeuwpopsneeuw on 17-Jan-18.
 */
public class FloorsController  {

    private ArrayList<FloorModel> floorModels = new ArrayList<>();
    private FloorsView view = new FloorsView();
    private int currentFloor = 0;

    public FloorsController() {
        FloorModel floor = new FloorModel();
        floor.registerView(view);
        floorModels.add(floor);
    }

    public void setCurrentFloor(int currentFloor) {
        if (currentFloor < 0 || currentFloor >= floorModels.size())
            return;

        this.currentFloor = currentFloor;
        for (int i = 0; i < floorModels.size(); i++)
            floorModels.get(i).setCurrentFloor(i == currentFloor);
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public ArrayList<FloorModel> getFloorModels() {
        return floorModels;
    }
}
