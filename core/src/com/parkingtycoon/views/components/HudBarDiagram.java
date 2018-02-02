package com.parkingtycoon.views.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.parkingtycoon.helpers.FixedRingArray;
import com.parkingtycoon.helpers.Remapper;
import com.parkingtycoon.models.ui.DiagramModel;

public class HudBarDiagram extends HudDiagram {

    private int start;
    private int maxStepSize = 1000;
    private Color gridColor = new Color(0.2f, 0.2f, 0.2f, 1);
    private int gridStepSize = 50;


    public HudBarDiagram(int width, int height, DiagramModel... diagramModels) {
        super(width, height, diagramModels);
    }

    @Override
    public void drawDiagram(ShapeRenderer shapeRenderer, DiagramModel[] diagramModels) {
        if (diagramModels != null && diagramModels.length > 0)
            start = (int)Remapper.map(startPercentage, 0, 1, 0, diagramModels[0].getMaxX());

        float dataMaxValue = 0;
        for (DiagramModel diagramModel : diagramModels)
            if (dataMaxValue < diagramModel.getMaxY())
                dataMaxValue = diagramModel.getMaxY();
        dataMaxValue *= 1.1F;


        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(gridColor);
        shapeRenderer.rect(0, 0, width, height);

        Gdx.gl.glLineWidth(1);
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GRAY);
        for (int x=1; x < gridStepSize; x++) {
            shapeRenderer.line(x*(width/gridStepSize), 0, x*(width/gridStepSize), height);
        }

        for (int y=1; y < gridStepSize; y++) {
            shapeRenderer.line(0, y*(height/gridStepSize), width,  y*(height/gridStepSize));
        }

        shapeRenderer.end();
        shapeRenderer.begin();

        Gdx.gl.glLineWidth(3);
        shapeRenderer.setColor(Color.GREEN);
        for (DiagramModel diagramModel : diagramModels) {
            FixedRingArray data = diagramModel.getHistory();

            int prevX = 0;
            for (int x = start; x < data.size(); x += Math.max(1, data.size() / (float) maxStepSize)) {
//                float x1 = Remapper.map(prevX, start, data.size(), 0, width);
                float x2 = Remapper.map(x, start, data.size(), 0, width);
                float p1 = Remapper.map(data.get(prevX), 0, dataMaxValue, 0, height);
                float p2 = Remapper.map(data.get(x), 0, dataMaxValue, 0, height);
                if (p1 < p2)
                    shapeRenderer.line(x2, p1, x2, p2);
                prevX = x;
            }
        }

        shapeRenderer.setColor(Color.RED);
        for (DiagramModel diagramModel : diagramModels) {
            FixedRingArray data = diagramModel.getHistory();

            int prevX = 0;
            for (int x = start; x < data.size(); x += Math.max(1, data.size() / (float) maxStepSize)) {
//                float x1 = Remapper.map(prevX, start, data.size(), 0, width);
                float x2 = Remapper.map(x, start, data.size(), 0, width);
                float p1 = Remapper.map(data.get(prevX), 0, dataMaxValue, 0, height);
                float p2 = Remapper.map(data.get(x), 0, dataMaxValue, 0, height);
                if (p1 > p2)
                    shapeRenderer.line(x2, p1, x2, p2);
                prevX = x;
            }
        }

        for (DiagramModel diagramModel : diagramModels) {
            shapeRenderer.setColor(diagramModel.getColor());
            float average = Remapper.map(diagramModel.getAverageValue(), 0, dataMaxValue, 0, height);
            shapeRenderer.line(0, average, width, average);
        }
    }
}
