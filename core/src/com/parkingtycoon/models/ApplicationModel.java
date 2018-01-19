package com.parkingtycoon.models;

import com.parkingtycoon.interfaces.Updatable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hilko on 18-1-2018.
 */
public class ApplicationModel extends BaseModel {

    public List<Updatable> updatables = new ArrayList<>();
    public double updates = 0, updatesPerSecond = 60;
    public boolean isSimulatorRunning = true, paused = false;

    public ApplicationModel() {

    }

}
