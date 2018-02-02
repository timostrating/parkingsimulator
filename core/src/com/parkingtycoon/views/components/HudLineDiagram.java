package com.parkingtycoon.views.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.parkingtycoon.helpers.Remapper;
import com.parkingtycoon.models.ui.DiagramModel;

import java.util.ArrayList;

public class HudLineDiagram extends HudDiagram {

    private int start = 1;
    private int maxStepSize = 5000;


    public HudLineDiagram(int width, int height, DiagramModel... diagramModels) {
        super(width, height, diagramModels);
    }

    @Override
    public void drawDiagram(ShapeRenderer shapeRenderer, DiagramModel[] diagramModels) {
        float dataMaxValue = 0;
        for (DiagramModel diagramModel : diagramModels)
            if (dataMaxValue < diagramModel.getMaxY())
                dataMaxValue = diagramModel.getMaxY();
        dataMaxValue *= 1.1F;

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

        Gdx.gl.glLineWidth(3);

        for (DiagramModel diagramModel : diagramModels) {
            ArrayList<Float> data = diagramModel.getHistory();

            shapeRenderer.setColor(Color.BLACK);
            int prevX = 0;
            for (int x = start; x < data.size(); x += Math.max(1, data.size() / (float) maxStepSize)) {
                float x1 = Remapper.map(prevX, start, data.size(), 0, width);
                float x2 = Remapper.map(x, start, data.size(), 0, width);
                float p1 = Remapper.map(data.get(prevX), 0, dataMaxValue, 0, height);
                float p2 = Remapper.map(data.get(x), 0, dataMaxValue, 0, height);
                shapeRenderer.line(x1 + 3, p1 - 5, x2 + 3, p2 - 5);
                prevX = x;
            }

            shapeRenderer.setColor(diagramModel.getColor());
            prevX = 0;
            for (int x = start; x < data.size(); x += Math.max(1, data.size() / (float) maxStepSize)) {
                float x1 = Remapper.map(prevX, start, data.size(), 0, width);
                float x2 = Remapper.map(x, start, data.size(), 0, width);
                float p1 = Remapper.map(data.get(prevX), 0, dataMaxValue, 0, height);
                float p2 = Remapper.map(data.get(x), 0, dataMaxValue, 0, height);
                shapeRenderer.line(x1, p1, x2, p2);
                prevX = x;
            }
        }
    }

    public void setStartPoint(int value) {
        start = (value < 1)? 1 : value;
    }

}
