package com.parkingtycoon.models.ui;

import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.models.BaseModel;
import java.util.ArrayList;

/**
 * This Class is responsible storing the X and Y data of the diagrams.
 */
public class DiagramModel extends BaseModel {

    private String name;
    private float maxX;
    private float maxY;
    private ArrayList<Vector2> history = new ArrayList<>();


    public DiagramModel(String name) {
        this.name = name;
    }

    public void addToHistory(float itemX) {
        history.add(new Vector2(itemX, maxY + 1));
    }

    public void addToHistory(Vector2 item) {
        if (maxX < item.x)
            maxX = item.x;

        if (maxY < item.y)
            maxY = item.x;

        history.add(item);
        notifyViews();
    }


    public String getName() {
        return name;
    }

    public float getMaxX() {
        return maxX;
    }

    public float getMaxY() {
        return maxY;
    }

    public ArrayList<Vector2> getHistory() {
        return history;
    }

}