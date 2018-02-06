package com.parkingtycoon.views.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.helpers.FixedRingArray;
import com.parkingtycoon.helpers.Remapper;
import com.parkingtycoon.models.ui.DiagramModel;

/**
 * This class is responsible for extending the HudDiagram with a line diagram.
 *
 * @author Timo Strating
 */
public class HudLineDiagram extends HudDiagram {

    private int start = 1;
    private int maxStepSize = 5000;
    private Vector2 shadowOffset = new Vector2(-3, 5);
    private float dataMaxValue;

    /**
     * The Standard Constructor for creating a Bar Diagram.
     *
     * @param width the width of the diagram.
     * @param height the height of the diagram.
     * @param diagramModels all the models where we later on select on
     */
    public HudLineDiagram(int width, int height, DiagramModel... diagramModels) {
        super(width, height, diagramModels);
    }

    /**
     * Draw Line Diagrams
     * @param shapeRenderer the renderer where we can draw on.
     * @param diagramModels the selected models that we should draw.
     */
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

    /**
     * Draw one line
     *
     * @param shapeRenderer the renderer where we can draw on.
     * @param data the Ring array with data.
     * @param color the color of the line.
     * @param offset the offset of the line.
     */
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
