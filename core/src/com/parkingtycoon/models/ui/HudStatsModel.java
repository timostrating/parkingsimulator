package com.parkingtycoon.models.ui;

import com.parkingtycoon.models.BaseModel;

import java.util.ArrayList;

/**
 * This Class is responsible storing the animation frames for animated sprites.
 */
public class HudStatsModel extends BaseModel {
    ArrayList<Integer> history = new ArrayList<>();

    public void addToHistory(Integer item) {
        history.add(item);
    }
}
