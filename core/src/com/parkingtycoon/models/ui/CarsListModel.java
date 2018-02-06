package com.parkingtycoon.models.ui;

import com.parkingtycoon.models.BaseModel;

/**
 * This class is responsible for holding the cars data
 */
public class CarsListModel extends BaseModel {
    public int totalCars = 0;
    public int maxCarsAtOnce = 0;
    public long totalUpdates = 0;
    public int parkedCars = 0;


    public void setData(int totalCars, long totalUpdates, int parkedCars) {
        this.totalCars = totalCars;
        this.totalUpdates = totalUpdates;
        this.parkedCars = parkedCars;

        if (maxCarsAtOnce < totalCars)
            maxCarsAtOnce = totalCars;

        notifyViews();
    }
}
