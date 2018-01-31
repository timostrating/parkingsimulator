package com.parkingtycoon.views.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.parkingtycoon.helpers.Remapper;

public class HudLineDiagram extends HudDiagram {

    private int start = 1;
    private int maxStepSize = 500;


    public HudLineDiagram(int width, int height) {
        super(width, height);
    }

    @Override
    public void drawDiagram(ShapeRenderer shapeRenderer) {
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0.2f, 0.2f, 0.2f, 1));
        shapeRenderer.rect(0, 0, width, height);

        Gdx.gl.glLineWidth(1);
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GRAY);
        for (int x=1; x < 50; x++) {
            shapeRenderer.line(x*(width/50), 0, x*(width/50), height);
        }

        for (int y=1; y < 50; y++) {
            shapeRenderer.line(0, y*(height/50), width,  y*(height/50));
        }

        shapeRenderer.end();
        shapeRenderer.begin();

        Gdx.gl.glLineWidth(5);
        shapeRenderer.setColor(Color.BLACK);
        if (data != null && data.length > 5) {

            int prevX = 0;
            for (int x=start; x<data.length; x += Math.max(1, data.length / (float)maxStepSize)) {
                float x1 = Remapper.map(prevX, start, data.length, 0, width);
                float x2 = Remapper.map(x, start, data.length, 0, width);
                float p1 = Remapper.map(data[prevX], 0, dataMaxValue, 0, height);
                float p2 = Remapper.map(data[x], 0, dataMaxValue, 0, height);
                shapeRenderer.line(x1+5, p1-7, x2+5, p2-7);
                prevX = x;
            }
        }

        shapeRenderer.setColor(Color.YELLOW);
        if (data != null && data.length > 5) {

            int prevX = 0;
            for (int x=start; x<data.length; x += Math.max(1, data.length / (float)maxStepSize)) {
                float x1 = Remapper.map(prevX, start, data.length, 0, width);
                float x2 = Remapper.map(x, start, data.length, 0, width);
                float p1 = Remapper.map(data[prevX], 0, dataMaxValue, 0, height);
                float p2 = Remapper.map(data[x], 0, dataMaxValue, 0, height);
                shapeRenderer.line(x1, p1, x2, p2);
                prevX = x;
            }
        }
    }

    public void setStartPoint(int value) {
        start = (value < 1)? 1 : value;
    }

}
