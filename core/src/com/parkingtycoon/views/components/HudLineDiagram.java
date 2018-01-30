package com.parkingtycoon.views.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.parkingtycoon.helpers.Remapper;

public class HudLineDiagram extends HudDiagram {

    private int start = 1;


    public HudLineDiagram(int width, int height) {
        super(width, height);
    }

    @Override
    public void drawDiagram(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(0, 0, width, height);

        shapeRenderer.setColor(Color.YELLOW);
        if (data != null && data.length > 5) {
            for (int x=start; x<data.length; x++) {
                float x1 = Remapper.map(x-1, start, data.length, 0, width);
                float x2 = Remapper.map(x, start, data.length, 0, width);
                float p1 = Remapper.map(data[x - 1], 0, dataMaxValue, 0, height);
                float p2 = Remapper.map(data[x], 0, dataMaxValue, 0, height);
                shapeRenderer.line(x1, p1, x2, p2);
            }
        }
    }

    public void setStartPoint(int value) {
        start = (value < 1)? 1 : value;
    }

}
