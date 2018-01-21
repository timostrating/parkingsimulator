package com.parkingtycoon.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.parkingtycoon.Game;
import com.parkingtycoon.controllers.InputController;

public class Hud {

    public Stage stage;
    private Skin skin;

    private Camera camera;
    private Viewport viewport;



    public Hud(SpriteBatch batch) {
        camera = new OrthographicCamera();
        viewport = new FitViewport(Game.V_WIDTH, Game.V_HEIGHT, camera);
        stage = new Stage(viewport, batch);

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        final TextButton button = new TextButton("Click me", skin, "default");
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

        Gdx.input.setInputProcessor(new InputMultiplexer(new InputController(), stage));
    }
}
