package com.parkingtycoon.views.components;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.parkingtycoon.models.ui.DiagramModel;

public class HudPieDiagram extends HudDiagram {
    public HudPieDiagram(int width, int height, DiagramModel... diagramModels) {
        super(width, height, diagramModels);
    }

    @Override
    void drawDiagram(ShapeRenderer shapeRenderer, DiagramModel[] diagramModels) {

    }

//    private float totalOfAllValues;
//
//    private ArrayList<PieModelAdapter> pieData = new ArrayList<>();
//    private HashMap<String, Integer> nameToPieIndex = new HashMap<>();
//
//    public HudPieDiagram(int width, int height) {
//        super(width, height);
//    }
//
//    @Override
//    public void drawDiagram(ShapeRenderer shapeRenderer, DiagramModel[] diagramModels) {
//        Gdx.gl.glLineWidth(3);
//        shapeRenderer.setColor(Color.WHITE);
//        shapeRenderer.circle(width/2, height/2, width/2, height/2);
//
//        shapeRenderer.setColor(Color.YELLOW);
//        if (pieData != null) {
//            float prevAngle = 0;
//            for (int x=0; x<pieData.size(); x++) {
//                float angle = Remapper.map(pieData.get(x).value, 0, totalOfAllValues, 0, 360);
//                shapeRenderer.arc(width/2, height/2, width/2, prevAngle, prevAngle + angle);
//                prevAngle += angle;
//            }
//        }
//    }

}
