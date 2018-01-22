package com.parkingtycoon.controllers;

import com.parkingtycoon.helpers.Random;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.CarQueueModel;

import java.util.ArrayList;

public class EntrancesController extends CarQueuesController {

    private static final int POP_INTERVAL = 10;

//    private int
    private ArrayList<CarQueueModel> entrances = new ArrayList<>();

    @Override
    public void addToQueue(CarModel car) {
        int i = Random.R.nextInt(entrances.size());
//        entrances.get(i).cars.add(car);
    }

    @Override
    public void update() {

    }

}
