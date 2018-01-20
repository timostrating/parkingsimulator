package com.parkingtycoon.models;

import com.parkingtycoon.interfaces.Updatable;

import java.util.ArrayList;

public class SimulationModel extends BaseModel {

    public ArrayList<Updatable> updatables = new ArrayList<>();
    public int updatesPerSecond = REAL_TIME_UPDATES_PER_SECOND;
    public int updates;
    public float deltaTime;

    public static final int REAL_TIME_UPDATES_PER_SECOND = 20;

}
