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
    private float dataMaxValue;

    public HudBarDiagram(int width, int height, DiagramModel... diagramModels) {
        super(width, height, diagramModels);
    }

    @Override
    public void drawDiagram(ShapeRenderer shapeRenderer, DiagramModel[] diagramModels) {
        dataMaxValue = getMaxYAllModels(diagramModels) * 1.1F;

        if (diagramModels != null && diagramModels.length > 0)
            start = (int)Remapper.map(startPercentage, 0, 1, 0, diagramModels[0].getMaxX());

        generateShapeGrid(shapeRenderer);

        Gdx.gl.glLineWidth(3);

        shapeRenderer.setColor(Color.GREEN);
        for (DiagramModel diagramModel : diagramModels)
            drawDiagramBar(shapeRenderer, diagramModel.getHistory(), false);

        shapeRenderer.setColor(Color.RED);
        for (DiagramModel diagramModel : diagramModels)
            drawDiagramBar(shapeRenderer, diagramModel.getHistory(), true);

        for (DiagramModel diagramModel : diagramModels) {
            shapeRenderer.setColor(diagramModel.getColor());
            float average = Remapper.map(diagramModel.getAverageValue(), 0, dataMaxValue, height, 0);
            shapeRenderer.line(0, average, width, average);
        }
    }

    private void drawDiagramBar(ShapeRenderer shapeRenderer, FixedRingArray data, boolean trigger) {
        int prevX = 0;
        for (int x = start; x < data.size(); x ++) { // = Math.max(1, data.size() / (float) maxStepSize)
//                float x1 = Remapper.map(prevX, start, data.size(), 0, width);
            float x2 = Remapper.map(x, start, data.size(), 0, width);
            float p1 = Remapper.map(data.get(prevX), 0, dataMaxValue, height, 0);
            float p2 = Remapper.map(data.get(x), 0, dataMaxValue, height, 0);
            if ((trigger) ? (p1 < p2) : (p1 > p2))
                shapeRenderer.line(x2, p1, x2, p2);
            prevX = x;
        }
    }
}
