package com.parkingtycoon.models;

import java.util.ArrayList;

/**
 * This Class is responsible for storing a Queue of Cars.
 */
public class CarQueueModel extends BaseModel {

    public int popTimer; // when popTimer == POP_INTERVAL -> pop car

    public ArrayList<CarModel> cars = new ArrayList<>();

}
