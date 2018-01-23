package com.parkingtycoon.views.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.parkingtycoon.CompositionRoot;

public class TestGraphView extends VisWindow {

    private final ShapeRenderer shapeRenderer;
    private final CompositionRoot root;
    private float graphSize = 400;
    private int steps;

    public TestGraphView() {
        super("TestGraphView");

        addCloseButton();
        closeOnEscape();

        shapeRenderer = new ShapeRenderer();

        root = CompositionRoot.getInstance();

    }

    public void update() {
        float BottomLeftX = Gdx.graphics.getWidth() / 2 - graphSize / 2;
        float BottomLeftY = Gdx.graphics.getHeight() / 2 - graphSize / 2;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.rect(BottomLeftX, BottomLeftY, graphSize, graphSize);
            shapeRenderer.line(0,0,400,400);
//            shapeRenderer.setColor(0, 1, 0, 1);
            shapeRenderer.circle(50,50,80);
        shapeRenderer.end();
    }
}
