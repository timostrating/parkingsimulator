package com.parkingtycoon.views.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.helpers.FixedRingArray;
import com.parkingtycoon.helpers.Remapper;
import com.parkingtycoon.models.ui.DiagramModel;

public class HudLineDiagram extends HudDiagram {

    private int start = 1;
    private int maxStepSize = 5000;
    private Vector2 shadowOffset = new Vector2(-3, 5);
    private float dataMaxValue;


    public HudLineDiagram(int width, int height, DiagramModel... diagramModels) {
        super(width, height, diagramModels);
    }

    @Override
    public void drawDiagram(ShapeRenderer shapeRenderer, DiagramModel[] diagramModels) {
        dataMaxValue = getMaxYAllModels(diagramModels) * 1.1F;

        if (diagramModels != null && diagramModels.length > 0)
            start = (int)Remapper.map(startPercentage, 0, 1, 0, diagramModels[0].getMaxX());

        generateShapeGrid(shapeRenderer);

        Gdx.gl.glLineWidth(3);

        for (DiagramModel diagramModel : diagramModels) {
            drawDiagramLine(shapeRenderer, diagramModel.getHistory(), Color.BLACK, shadowOffset);
            drawDiagramLine(shapeRenderer, diagramModel.getHistory(), diagramModel.getColor(), Vector2.Zero);
        }
    }

    private void drawDiagramLine(ShapeRenderer shapeRenderer, FixedRingArray data, Color color, Vector2 offset) {
        shapeRenderer.setColor(color);
        int prevX = 0;
        for (int x = start; x < data.size(); x ++) { // Math.max(1, data.size() / (float) maxStepSize)
            float x1 = Remapper.map(prevX, start, data.size(), 0, width);
            float x2 = Remapper.map(x, start, data.size(), 0, width);
            float p1 = Remapper.map(data.get(prevX), 0, dataMaxValue, height, 0);
            float p2 = Remapper.map(data.get(x), 0, dataMaxValue, height, 0);
            shapeRenderer.line(x1 + offset.x, p1 + offset.y, x2 + offset.x, p2 + offset.y);
            prevX = x;
        }
    }

}
