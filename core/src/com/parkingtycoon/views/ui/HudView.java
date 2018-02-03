package com.parkingtycoon.views.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;
import com.parkingtycoon.helpers.ui.GameWindowResizeEvent;

public class HudView implements Disposable {
    private final CompositionRoot root = CompositionRoot.getInstance();;

    public Stage stage;

    private OrthographicCamera hudCamera;
    private Viewport viewport;

    private SpriteBatch hudBatch;


    public HudView() {
        hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());
        hudCamera.setToOrtho(false, Game.VIEWPORT_WIDTH, Game.VIEWPORT_HEIGHT);

        viewport = new ScreenViewport();
        stage = new Stage(viewport, root.game.batch);
        stage.getViewport().setCamera(hudCamera);

        hudBatch = new SpriteBatch();
        hudBatch.setProjectionMatrix(hudCamera.combined);

        VisUI.load();

        root.game.inputMultiplexer.addProcessor(0, stage);
    }

    public void resize(int width, int height) {
        hudCamera.setToOrtho(false,  width, height);
        viewport.update(width, height);

        GameWindowResizeEvent resizeEvent = new GameWindowResizeEvent();
        for (int i=0; i<stage.getActors().size; i++)  // We can not use a iterator here because of Reentrancy thread locking https://stackoverflow.com/questions/16504231/reentrancy-in-java
            stage.getActors().get(i).fire(resizeEvent);
    }

    @Override
    public void dispose() {
        VisUI.dispose();
        stage.dispose();
    }

    public void render() {
        hudCamera.update();

        hudBatch.begin();
        stage.act();
        stage.draw();
        hudBatch.end();
    }

}
