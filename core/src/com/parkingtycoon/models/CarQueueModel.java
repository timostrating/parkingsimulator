package com.parkingtycoon.models;

import java.util.ArrayList;

public class CarQueueModel extends BaseModel {

    public int popTimer; // when popTimer == POP_INTERVAL -> pop car

    public ArrayList<CarModel> cars = new ArrayList<>();

}
