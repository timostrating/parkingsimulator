package com.parkingtycoon.views.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.parkingtycoon.helpers.Remapper;
import com.parkingtycoon.models.ui.DiagramModel;

public class HudPieDiagram extends HudDiagram {

    private float quality = 2;


    public HudPieDiagram(int width, int height, DiagramModel... diagramModels) {
        super(width, height, diagramModels);
    }

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

    private int getQuality(float degrees) {
        return Math.max(1, (int)(6 * (float)Math.cbrt(width/2 -20) * (degrees / (360.0f / quality))));
    }

}
