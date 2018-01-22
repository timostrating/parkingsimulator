package com.parkingtycoon.controllers;

import com.parkingtycoon.models.FloorModel;
import com.parkingtycoon.views.FloorsView;

import java.util.ArrayList;

/**
 * Created by Sneeuwpopsneeuw on 17-Jan-18.
 */
public class FloorsController  {

    private ArrayList<FloorModel> floors = new ArrayList<>();
    private FloorsView view;
    private int currentFloor = 0;

    public FloorsController() {
        FloorModel floor = new FloorModel();
        view = new FloorsView();
        floor.registerView(view);
        floors.add(floor);

        setCurrentFloor(0);
    }

    public void setCurrentFloor(int currentFloor) {
        if (currentFloor < 0 || currentFloor >= floors.size())
            return;

        this.currentFloor = currentFloor;
        for (int i = 0; i < floors.size(); i++)
            floors.get(i).setCurrentFloor(i == currentFloor);
    }

}
