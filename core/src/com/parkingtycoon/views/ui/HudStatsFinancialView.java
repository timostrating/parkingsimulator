package com.parkingtycoon.views.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
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

    public HudStatsFinancialView(Stage stage) {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(true, 200, 200);
        camera.position.set(100,100,0);
        camera.update();

        FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888,200, 200, false);

        frameBuffer.begin();  // BEGIN
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(0, 0, 200, 200);
        shapeRenderer.line(0,0,200,200);
        shapeRenderer.circle(100,100,100);
        shapeRenderer.end();

        frameBuffer.end();    // end
        Texture texture = frameBuffer.getColorBufferTexture();

        HubStatsWindow window = new HubStatsWindow(texture);
        stage.addActor(window);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void updateView(BaseModel model) {

    }

    @Override
    public float renderIndex() {
        return 0;
    }


    class HubStatsWindow extends VisWindow {

        HubStatsWindow(Texture texture) {
            super("Stats");
            setSize(300, 300);
            addCloseButton();

            Image image = new Image();
            TextureRegion textureRegion = new TextureRegion(texture, 0, 0, 200, 200);
            Drawable drawable = new TextureRegionDrawable(textureRegion);

            image.setDrawable(drawable);
            add(image).expand();
        }
    }
}
