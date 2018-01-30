package com.parkingtycoon.views.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.parkingtycoon.helpers.Remapper;

public class HudBarDiagram extends HudDiagram {

    public HudBarDiagram(int width, int height) {
        super(width, height);
    }

    @Override
    public void drawDiagram(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(width/2, height/2, width/2, height/2);

        shapeRenderer.setColor(Color.YELLOW);
        if (data != null && data.length > 5) {
            for (int x=0; x<data.length; x+=20) {
//                float x1 = Remapper.map(x-1, start, data.length, 0, width);
//                float x2 = Remapper.map(x, start, data.length, 0, width);
//                float p1 = Remapper.map(data[x - 1], 0, dataMaxValue, 0, height);
                float p2 = Remapper.map(data[x], 0, dataMaxValue, 0, height);
                shapeRenderer.rect(x, 0, 20-2, p2);
            }
        }
    }

}
