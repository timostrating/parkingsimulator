package com.parkingtycoon.models;

import com.parkingtycoon.interfaces.Updatable;

import java.util.ArrayList;

public class SimulationModel extends BaseModel {

    public ArrayList<Updatable> updatables = new ArrayList<>();
    public int updatesPerSecond = 20;
    public int updates;
    public float deltaTime;

}
