package com.parkingtycoon.views.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.parkingtycoon.models.ui.DiagramModel;

import java.util.ArrayList;

import static com.parkingtycoon.models.ui.DiagramModel.DiagramModelType;

/**
 * This class is responsible for setting up the required systems to show a diagram in the Hud.
 *
 * This is done be rendering the diagram in a separate buffer and returning a texture to the hud.
 *
 * _EXAMPLE_
 * frameBuffer {
 *   shapeRenderer {
 *     abstract drawDiagram(shapeRenderer)
 *   }
 * }
 *
 * return frameBuffer.texture
 *
 */
public abstract class HudDiagram {

    private OrthographicCamera camera;

    private DiagramModel[] diagramModels;
    private ArrayList<DiagramModelType> selectedDiagramsModels;

    protected int width;
    protected int height;
    protected float startPercentage;
    private FrameBuffer frameBuffer;
    private ShapeRenderer shapeRenderer;

    private Color gridColor = new Color(0.2f, 0.2f, 0.2f, 1);
    private int gridStepSize = 50;
    private float dataMaxValue;
    private SpriteBatch batch;
    BitmapFont font = new BitmapFont(); //or use alex answer to use custom font

    /**
     * This is the standard constructor of the class
     * @arg width This is the width that is used to calculate the scale of the diagram
     * @arg height This is the height that is used to calculate the scale of the diagram
     * @arg diagramModels We would like to know all the models before we srtart, later on you can specifie hat selection of models you want to show.
     */
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
        batch = new SpriteBatch();
    }

    /**
     * Generate the models you selected and return the data as a Texture.
     * @arg selectedDiagramsModels Give a List of diagram types, Every diagram with the same type as one in your selection will be shown.
     */
    public Texture generateDiagramTexture(ArrayList<DiagramModelType> selectedDiagramsModels) {
        this.selectedDiagramsModels = selectedDiagramsModels;

        frameBuffer.begin();  // BEGIN
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawShapes();

        batch.begin();
        font.getData().setScale(5);
        font.draw(batch, "Time ->", width*2, 100);
        batch.end();

        frameBuffer.end();    // END
        return frameBuffer.getColorBufferTexture();
    }

    /**
     * Setup a ShapeRenderer (this automaticly stores all the data in the framebuffer When this function is called while the framebuffer is active)
     * 
     */
    private void drawShapes() {
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);  // BEGIN

        drawDiagram(shapeRenderer, getSelectedDiagrams());

        shapeRenderer.end();    // END
    }

    /**
     * This is a helper for the Children. It returns the highest Y value of all the models you give him/
     */
    protected float getMaxYAllModels(DiagramModel[] models) {
        float dataMaxValue = 0;
        for (DiagramModel diagramModel : models)
            if (dataMaxValue < diagramModel.getMaxY())
                dataMaxValue = diagramModel.getMaxY();

        return dataMaxValue;
    }

    /**
     * This is a helper for the Children. This generates a nicly looking grid of lines.
     *
     * @arg shapeRenderer This is the renderer where the grid will be added.
     */
    protected void generateShapeGrid(ShapeRenderer shapeRenderer) {
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(gridColor);
        shapeRenderer.rect(0, 0, width, height);

        Gdx.gl.glLineWidth(1);
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GRAY);
        for (int x=1; x < gridStepSize; x++)
            shapeRenderer.line(x*(width/gridStepSize), 0, x*(width/gridStepSize), height);

        for (int y=1; y < gridStepSize; y++)
            shapeRenderer.line(0, y*(height/gridStepSize), width,  y*(height/gridStepSize));

        shapeRenderer.end();    // This is required to support multiple line widths in one ShapeRenderer
        shapeRenderer.begin();
    }

    /**
     * Use our selection to get all the models that we have selected
     */
    private DiagramModel[] getSelectedDiagrams() {
        ArrayList<DiagramModel> returnValue = new ArrayList<>();
        for (DiagramModel diagramModel : diagramModels)
            if (selectedDiagramsModels.contains(diagramModel.getDiagramModelType()))
                returnValue.add(diagramModel);

//        Logger.info(diagramModels.length+" "+selectedDiagramsModels.size()+" "+returnValue.size());
        return returnValue.toArray(new DiagramModel[returnValue.size()]);
    }

    abstract void drawDiagram(ShapeRenderer shapeRenderer, DiagramModel[] diagramModels); // ABSTRACT

    /**
     * Setter for startPercentage
     *
     * @arg startPercentage The new value
     */
    public void setStartPercentage(float startPercentage) {
        this.startPercentage = startPercentage;
    }
}
