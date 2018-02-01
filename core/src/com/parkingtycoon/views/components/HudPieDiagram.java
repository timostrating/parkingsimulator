package com.parkingtycoon.views.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.parkingtycoon.helpers.Remapper;
import com.parkingtycoon.models.ui.DiagramModel;

import java.util.ArrayList;
import java.util.HashMap;

public class HudPieDiagram extends HudDiagram {

    private float totalOfAllValues;

    private ArrayList<PieModelAdapter> pieData = new ArrayList<>();
    private HashMap<String, Integer> nameToPieIndex = new HashMap<>();

    public HudPieDiagram(int width, int height) {
        super(width, height);
    }

    @Override
    public void drawDiagram(ShapeRenderer shapeRenderer) {
        Gdx.gl.glLineWidth(3);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(width/2, height/2, width/2, height/2);

        shapeRenderer.setColor(Color.YELLOW);
        if (pieData != null) {
            float prevAngle = 0;
            for (int x=0; x<pieData.size(); x++) {
                float angle = Remapper.map(pieData.get(x).value, 0, totalOfAllValues, 0, 360);
                shapeRenderer.arc(width/2, height/2, width/2, prevAngle, prevAngle + angle);
                prevAngle += angle;
            }
        }
    }

    @Override
    public void update(DiagramModel model) {
        // we don't call super because we do not want the data variable to be set by it.

        if (nameToPieIndex.containsKey( model.getName() )) {
            PieModelAdapter adapter = pieData.get( nameToPieIndex.get( model.getName() ) );
            adapter.name = model.getName();
            adapter.color = model.getColor();
            totalOfAllValues -= adapter.value;
            adapter.value = model.getTotalValue();
            totalOfAllValues += adapter.value;

        } else {
            pieData.add(new PieModelAdapter(model.getName(), model.getColor(), model.getTotalValue()));
            totalOfAllValues += model.getTotalValue();
            nameToPieIndex.put(model.getName(), pieData.size()-1);
        }
    }


    /**
     * Java bean
     *
     * More info: https://nl.wikipedia.org/wiki/JavaBeans
     */
    private class PieModelAdapter {
        private String name;
        private Color color;
        private float value;

        public PieModelAdapter(String name, Color color, float value) {
            this.name = name;
            this.color = color;
            this.value = value;
        }
    }

}
