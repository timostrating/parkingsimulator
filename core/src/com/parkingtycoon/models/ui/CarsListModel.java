package com.parkingtycoon.models.ui;

import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.CarModel;

import java.util.ArrayList;

public class CarsListModel extends BaseModel {
    public String title = "";
    public ArrayList<CarModel> carModels;

    public CarsListModel(String title, ArrayList<CarModel> carModels) {
        this.title = title;
        this.carModels = carModels;
    }

    public void redraw() {
        notifyViews();
    }
}
