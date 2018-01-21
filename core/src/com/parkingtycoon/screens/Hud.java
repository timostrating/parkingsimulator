package com.parkingtycoon.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.parkingtycoon.Game;

public class Hud {
    private Stage stage;
    private Skin skin;

    private Viewport viewport;

    TextButton textButton;

    public Hud(SpriteBatch batch) {
        viewport = new FitViewport(Game.V_WIDTH, Game.V_HEIGHT);
        stage = new Stage(viewport, batch);
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));


        final TextButton button = new TextButton("Click me", skin, "default");
        button.setWidth(200);
        button.setHeight(200);

        stage.addActor(button);
    }
}
