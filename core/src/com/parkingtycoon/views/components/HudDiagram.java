package com.parkingtycoon.views.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.parkingtycoon.models.ui.DiagramModel;

import java.util.ArrayList;

import static com.parkingtycoon.models.ui.DiagramModel.DiagramModelType;


public abstract class HudDiagram {

    private OrthographicCamera camera;

    private DiagramModel[] diagramModels;
    private ArrayList<DiagramModelType> selectedDiagramsModels;

    protected int width;
    protected int height;
    protected float startPercentage;
    private FrameBuffer frameBuffer;
    private ShapeRenderer shapeRenderer;
//    private SpriteBatch batch;
//    BitmapFont font = new BitmapFont(); //or use alex answer to use custom font


    public HudDiagram(int width, int height, DiagramModel... diagramModels) {
        this.width = width;
        this.height = height;
        this.diagramModels = diagramModels;

        camera = new OrthographicCamera();
        camera.setToOrtho(true, width, height);
        camera.position.set((width/2),(height/2),0);
        camera.update();

        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
        shapeRenderer = new ShapeRenderer();
//        batch = new SpriteBatch();
    }

    public Texture generateDiagramTexture(ArrayList<DiagramModelType> selectedDiagramsModels) {
        this.selectedDiagramsModels = selectedDiagramsModels;
        frameBuffer.begin();  // BEGIN
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawShapes();

//        batch.begin();
//        font.getData().setScale(10);
//        font.draw(batch, "Hello World!", 100, 100);
//        batch.end();

        frameBuffer.end();    // END
        return frameBuffer.getColorBufferTexture();
    }

    private void drawShapes() {
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);  // BEGIN

        drawDiagram(shapeRenderer, getActiveDiagrams());

        shapeRenderer.end();    // END
    }

    private DiagramModel[] getActiveDiagrams() {
        ArrayList<DiagramModel> returnValue = new ArrayList<>();
        for (DiagramModel diagramModel : diagramModels)
            if (selectedDiagramsModels.contains(diagramModel.getDiagramModelType()))
                returnValue.add(diagramModel);

//        Logger.info(diagramModels.length+" "+selectedDiagramsModels.size()+" "+returnValue.size());
        return returnValue.toArray(new DiagramModel[returnValue.size()]);
    }

    abstract void drawDiagram(ShapeRenderer shapeRenderer, DiagramModel[] diagramModels); // ABSTRACT

    public void update(ArrayList<DiagramModelType> selectedDiagramsModels) {
        this.selectedDiagramsModels = selectedDiagramsModels;
    }


    public void setStartPercentage(float startPercentage) {
        this.startPercentage = startPercentage;
    }
}
