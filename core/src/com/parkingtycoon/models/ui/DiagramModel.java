package com.parkingtycoon.models.ui;

import com.badlogic.gdx.graphics.Color;
import com.parkingtycoon.helpers.Random;
import com.parkingtycoon.models.BaseModel;
import java.util.ArrayList;

/**
 * This Class is responsible storing the X and Y data of the components.
 */
public class DiagramModel extends BaseModel {

    private String name;
    private float maxY;
    private float totalValue;
    private float averageValue;

    private Color color;
    private ArrayList<Float> history = new ArrayList<>();


    public DiagramModel(String name) {
        this.name = name;
        color = Random.randomColor();
    }

    public DiagramModel(String name, Color color) {
        this.name = name;
        this.color = color;
    }


    public void addToHistory(float itemY) {
        if (maxY < itemY)
            maxY = itemY;

        totalValue += itemY;
        averageValue = totalValue / (float)history.size();

        history.add(itemY);
        notifyViews();
    }


    // Getters
    public String getName() {
        return name;
    }

    public float getMaxX() {
        return history.size();
    }

    public float getMaxY() {
        return maxY;
    }

    public float getTotalValue() {
        return totalValue;
    }

    public float getAverageValue() {
        return averageValue;
    }

    public Color getColor() {
        return color;
    }

    public Float[] getHistory() {
        return history.toArray(new Float[history.size()]);
    }

}