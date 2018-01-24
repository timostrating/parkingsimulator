package com.parkingtycoon.models.ui;

import com.parkingtycoon.models.BaseModel;

import java.util.ArrayList;


public class HudStatsModel extends BaseModel {
    ArrayList<Integer> history = new ArrayList<>();

    public void addToHistory(Integer item) {
        history.add(item);
    }
}
