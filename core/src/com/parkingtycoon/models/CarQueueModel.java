package com.parkingtycoon.models;

import java.util.ArrayList;

public class CarQueueModel extends BuildableModel {

    public int popTimer; // when popTimer == POP_INTERVAL -> pop car

    public ArrayList<CarModel> cars = new ArrayList<>();

    public CarQueueModel(int x, int y) {
        super(x, y);
    }

}
