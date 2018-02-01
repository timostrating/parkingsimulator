package com.parkingtycoon.views.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.parkingtycoon.models.ui.DiagramModel;

public abstract class HudDiagram {

    private OrthographicCamera camera;

    protected Float[] data;

    protected int width;
    protected int height;
    private FrameBuffer frameBuffer;


    public HudDiagram(int width, int height) {
        this.width = width;
        this.height = height;

        camera = new OrthographicCamera();
        camera.setToOrtho(true, width, height);
        camera.position.set((width/2),(height/2),0);
        camera.update();

        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
    }

    public Texture generateDiagramTexture() {
        frameBuffer.begin();  // BEGIN
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawShapes();

        frameBuffer.end();    // END
        return frameBuffer.getColorBufferTexture();
    }

    private void drawShapes() {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin();  // BEGIN

        drawDiagram(shapeRenderer);

        shapeRenderer.end();    // END
    }

    public abstract void drawDiagram(ShapeRenderer shapeRenderer); // ABSTRACT

    public void update(DiagramModel model) {
        data = model.getHistory();
    }

    public int getDataLength() {
        return (data == null)? 0 : data.length;
    }

}