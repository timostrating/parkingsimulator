package com.parkingtycoon.models.ui;

import com.parkingtycoon.models.BaseModel;
import java.util.ArrayList;

/**
 * This Class is responsible storing the X and Y data of the components.
 */
public class DiagramModel extends BaseModel {

    private String name;
    private float maxY;
    private ArrayList<Float> history = new ArrayList<>();


    public DiagramModel(String name) {
        this.name = name;
    }

    public void addToHistory(float itemY) {
        if (maxY < itemY)
            maxY = itemY;

        history.add(itemY);
        notifyViews();
    }

    public String getName() {
        return name;
    }

    public float getMaxX() {
        return history.size();
    }

    public float getMaxY() {
        return maxY;
    }

    public Float[] getHistory() {
        return history.toArray(new Float[history.size()]);
    }

}