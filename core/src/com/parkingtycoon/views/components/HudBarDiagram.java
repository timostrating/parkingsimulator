package com.parkingtycoon.views.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.parkingtycoon.helpers.FixedRingArray;
import com.parkingtycoon.helpers.Remapper;
import com.parkingtycoon.models.ui.DiagramModel;

/**
 * This class is responsible for extending the HudDiagram with a bar like diagram.
 *
 * @author Timo Strating
 */
public class HudBarDiagram extends HudDiagram {

    private int start;
    private int maxStepSize = 1000;
    private float dataMaxValue;

    /**
     * The Standard Constructor for creating a Bar Diagram.
     *
     * @param width the width of the diagram.
     * @param height the height of the diagram.
     * @param diagramModels all the models where we later on select on
     */
    public HudBarDiagram(int width, int height, DiagramModel... diagramModels) {
        super(width, height, diagramModels);
    }

    /**
     * Draw bar Diagrams
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

    /**
     * Draw one line of bars in one direction
     *
     * @param shapeRenderer the renderer where we can draw on.
     * @param data the data for the bars.
     * @param flip which direction should we draw something.
     */
    private void drawDiagramBar(ShapeRenderer shapeRenderer, FixedRingArray data, boolean flip) {
        int prevX = 0;
        for (int x = start; x < data.size(); x ++) { // = Math.max(1, data.size() / (float) maxStepSize)
//                float x1 = Remapper.map(prevX, start, data.size(), 0, width);
            float x2 = Remapper.map(x, start, data.size(), 0, width);
            float p1 = Remapper.map(data.get(prevX), 0, dataMaxValue, height, 0);
            float p2 = Remapper.map(data.get(x), 0, dataMaxValue, height, 0);
            if ((flip) ? (p1 < p2) : (p1 > p2))
                shapeRenderer.line(x2, p1, x2, p2);
            prevX = x;
        }
    }
}
