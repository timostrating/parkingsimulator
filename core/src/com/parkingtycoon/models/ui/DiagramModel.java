package com.parkingtycoon.models.ui;

import com.badlogic.gdx.graphics.Color;
import com.parkingtycoon.helpers.FixedRingArray;
import com.parkingtycoon.helpers.Random;
import com.parkingtycoon.models.BaseModel;

/**
 * This Class is responsible storing the X and Y data of the components.
 */
public class DiagramModel extends BaseModel {

//    public final static int MAX_SIZE = 20_000;

    public enum DiagramModelType {
        MONEY,
        TOTAL_CARS,
        ADHOC_CARS,
        RESERVED_CARS,
        VIP_CARS
    }

    private DiagramModelType diagramModelType;
    private String name;
    private float maxY;
    private float totalValue;
    private float averageValue;

    private Color color;
    private FixedRingArray history = new FixedRingArray(100_000);


    public DiagramModel(String name, DiagramModelType diagramModelType) {
        this.name = name;
        this.diagramModelType = diagramModelType;
        color = Random.randomColor();
    }

    public DiagramModel(String name, DiagramModelType diagramModelType, Color color) {
        this.name = name;
        this.diagramModelType = diagramModelType;
        this.color = color;
    }

    public void addToHistory(float itemY) {
        if (maxY < itemY)
            maxY = itemY;

        totalValue += itemY;
        averageValue = totalValue / (float)history.size();

        history.put(itemY);

//        if (history.size() > MAX_SIZE)
//            for (int i=0; i<MAX_SIZE/10; i++)
//                history.remove(0);

        notifyViews();
    }


    // Getters
    public DiagramModelType getDiagramModelType() {
        return diagramModelType;
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

    public float getTotalValue() {
        return totalValue;
    }

    public float getAverageValue() {
        return averageValue;
    }

    public Color getColor() {
        return color;
    }

    public FixedRingArray getHistory() {
        return history;
    }

}