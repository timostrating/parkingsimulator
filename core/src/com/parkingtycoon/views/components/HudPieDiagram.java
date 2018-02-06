package com.parkingtycoon.views.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.parkingtycoon.helpers.Remapper;
import com.parkingtycoon.models.ui.DiagramModel;

/**
 * This class is responsible for extending the HudDiagram with a pie chart.
 *
 * @author Timo Strating
 */
public class HudPieDiagram extends HudDiagram {

    private float quality = 2;


    /**
     * The Standard Constructor for creating a pie Chart.
     *
     * @param width the width of the diagram.
     * @param height the height of the diagram.
     * @param diagramModels all the models where we later on select on
     */
    public HudPieDiagram(int width, int height, DiagramModel... diagramModels) {
        super(width, height, diagramModels);
    }

    /**
     * Draw the pie chart parts
     * @param shapeRenderer the renderer where we can draw on.
     * @param diagramModels the selected models that we should draw.
     */
    @Override
    void drawDiagram(ShapeRenderer shapeRenderer, DiagramModel[] diagramModels) {
        float totalOfAllModels = 0;
        for (DiagramModel diagramModel : diagramModels)
            totalOfAllModels += diagramModel.getTotalValue();

        Gdx.gl.glLineWidth(3);
        float prevAngle = 0;

        for (DiagramModel diagramModel : diagramModels) {
            float angle = Remapper.map(diagramModel.getTotalValue(), 0, totalOfAllModels, 0, 360);

            shapeRenderer.setColor(diagramModel.getColor().mul(0.7f));
            shapeRenderer.arc(width/2+7, height/2-7, width/2 -20, prevAngle, angle, getQuality(angle));
            diagramModel.getColor().mul(1/ 0.7f);

            prevAngle += angle;
        }

        for (DiagramModel diagramModel : diagramModels) {
            float angle = Remapper.map(diagramModel.getTotalValue(), 0, totalOfAllModels, 0, 360);

            shapeRenderer.setColor(diagramModel.getColor());
            shapeRenderer.arc(width/2, height/2, width/2 -20, prevAngle, angle, getQuality(angle));
            prevAngle += angle;
        }
    }

    /**
     * Calculate a nice number where seams and edges are not really visible.
     *
     * @param degrees the angle of the part.
     * @return a number that is dividable thru many number but that is still proportional to the chart when given quality number to the circle drawer.
     */
    private int getQuality(float degrees) {
        return Math.max(1, (int)(6 * (float)Math.cbrt(width/2 -20) * (degrees / (360.0f / quality))));
    }

}
