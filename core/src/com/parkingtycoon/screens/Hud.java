package com.parkingtycoon.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.parkingtycoon.controllers.InputController;

public class Hud implements Disposable {

//    private Texture myTexture;
    private Stage stage;
    private Skin skin;

    private OrthographicCamera camera;
    private Viewport viewport;

    private SpriteBatch hudBatch;


    public Hud(SpriteBatch batch) {
        camera = new OrthographicCamera();
        viewport = new ScreenViewport();
        stage = new Stage(viewport, batch);
        VisUI.load();

        hudBatch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        Texture buttonTexture = new Texture(Gdx.files.internal("badlogic.jpg"));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(buttonTexture));
        final VisImageButton button = new VisImageButton(drawable);
        button.setWidth(200);
        button.setHeight(200);

        final Dialog dialog = new Dialog("Click message", skin);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.show(stage);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        dialog.hide();
                    }
                }, 5);
            }
        });

        stage.addActor(button);

        Gdx.input.setInputProcessor(new InputMultiplexer(new InputController(), stage));  // TODO: the Game class may should do this.
    }

    @Override
    public void dispose() {
        stage.dispose();
        VisUI.dispose();
    }

    public void render() {
        camera.update();

        hudBatch.begin();
            stage.act();
            stage.draw();
        hudBatch.end();
    }
}
