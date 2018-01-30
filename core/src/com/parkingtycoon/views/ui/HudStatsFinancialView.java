package com.parkingtycoon.views.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.parkingtycoon.helpers.Remapper;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.ui.DiagramModel;
import com.parkingtycoon.views.BaseView;

public class HudStatsFinancialView extends BaseView {

    private final HubStatsWindow window;
    private OrthographicCamera camera;
    private Float[] data;
    private float dataMaxValue;

    public HudStatsFinancialView(Stage stage) {
        camera = new OrthographicCamera();
        camera.setToOrtho(true, 200, 200);
        camera.position.set(100,100,0);
        camera.update();

        window = new HubStatsWindow(generateDiagramTexture());
        stage.addActor(window);
    }

    private Texture generateDiagramTexture() {
        FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888,200, 200, false);

        frameBuffer.begin();  // BEGIN
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawShapes();

        frameBuffer.end();    // END
        return frameBuffer.getColorBufferTexture();
    }

    private void drawShapes() {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);  // BEGIN
        shapeRenderer.rect(0, 0, 200, 200);

        if (data != null && data.length > 5) {
            int start = data.length-100;
            if (start < 1)
                start = 1;

            for (int x=start; x<data.length; x++) {
                float x1 = Remapper.map(x-1, start, data.length, 0, 200);
                float x2 = Remapper.map(x, start, data.length, 0, 200);
                float p1 = Remapper.map(data[x - 1], 0, dataMaxValue, 0, 200);
                float p2 = Remapper.map(data[x], 0, dataMaxValue, 0, 200);
                shapeRenderer.line(x1, p1, x2, p2);
            }
        }

        shapeRenderer.end();                                // END
    }

    @Override
    public void start() {
        super.start();
    }

    private int flip = 0;
    
    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        if (flip % 30 == 0)
            window.setDigram(generateDiagramTexture());

        flip++;
    }

    @Override
    public void updateView(BaseModel model) {
        if (model instanceof DiagramModel) {
            data = ((DiagramModel) model).getHistory();
            dataMaxValue = ((DiagramModel) model).getMaxY() + 100;
        }
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
