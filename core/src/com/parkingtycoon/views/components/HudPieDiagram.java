package com.parkingtycoon.views.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.parkingtycoon.helpers.Remapper;
import com.parkingtycoon.models.ui.DiagramModel;

public class HudPieDiagram extends HudDiagram {

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
            shapeRenderer.setColor(diagramModel.getColor());
            float angle = Remapper.map(diagramModel.getTotalValue(), 0, totalOfAllModels, 0, 360);
            shapeRenderer.arc(width/2, height/2, width/2 -20, prevAngle,  angle);
            prevAngle += angle;
        }
    }

}
