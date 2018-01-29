package com.parkingtycoon.views.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.views.BaseView;

public class HudStatsFinancialView extends BaseView {

    private final HubStatsWindow window;
    private OrthographicCamera camera;

    public HudStatsFinancialView(Stage stage) {
        camera = new OrthographicCamera();
        camera.setToOrtho(true, 200, 200);
        camera.position.set(100,100,0);
        camera.update();

        window = new HubStatsWindow(generateDiagramTexture(Color.WHITE));
        stage.addActor(window);
    }

    private Texture generateDiagramTexture(Color color) {
        FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888,200, 200, false);

        frameBuffer.begin();  // BEGIN
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawShapes(color);

        frameBuffer.end();    // END
        return frameBuffer.getColorBufferTexture();
    }

    private void drawShapes(Color color) {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);  // BEGIN
        shapeRenderer.setColor(color);
        shapeRenderer.rect(0, 0, 200, 200);
        shapeRenderer.line(0,0,200,200);
        shapeRenderer.circle(100,100,100);
        shapeRenderer.end();                                // END
    }

    @Override
    public void start() {
        super.start();
    }

    private int flip = 1;
    
    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        if (flip % 60 == 0)
            window.setDigram(generateDiagramTexture(Color.RED));
        else if (flip % 60 == 30)
            window.setDigram(generateDiagramTexture(Color.BLUE));

        flip++;
    }

    @Override
    public void updateView(BaseModel model) {
        
    }

    @Override
    public float renderIndex() {
        return 0;
    }


    class HubStatsWindow extends VisWindow {
        Image image;

        HubStatsWindow(Texture texture) {
            super("Stats");
            setSize(300, 300);
            addCloseButton();

            image = new Image();
            TextureRegion textureRegion = new TextureRegion(texture, 0, 0, 200, 200);
            Drawable drawable = new TextureRegionDrawable(textureRegion);

            image.setDrawable(drawable);
            add(image).expand();
        }

        public void setDigram(Texture texture) {
            TextureRegion textureRegion = new TextureRegion(texture, 0, 0, 200, 200);
            Drawable drawable = new TextureRegionDrawable(textureRegion);
            image.setDrawable(drawable);
        }
    }
}
