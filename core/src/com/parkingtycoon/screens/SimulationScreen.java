package com.parkingtycoon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;
import com.parkingtycoon.views.EntranceView;
import com.parkingtycoon.views.TestSpriteView;

public class SimulationScreen implements Screen {


    final Game game;
    private CompositionRoot root;

    private OrthographicCamera worldCamera;
    private Hud hud;


    public SimulationScreen(Game game) {
        this.game = game;
        worldCamera = new OrthographicCamera();
        worldCamera.setToOrtho(false, 800, 480);
        CompositionRoot.getInstance().renderController.setMainCamera(worldCamera);
        root = CompositionRoot.getInstance();
        hud = new Hud(game.batch);

        for (int i = 0; i < 1000; i++)
            new TestSpriteView();

        new EntranceView();
    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        root.renderController.preRender();      // preRender views
        game.batch.setProjectionMatrix(worldCamera.combined);
        root.renderController.render();         // render views

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined); // render Hud
        hud.stage.draw();

        Gdx.graphics.setTitle("Parking Tycoon (fps: " + Gdx.graphics.getFramesPerSecond() + ")");
    }

    @Override
    public void resize(int width, int height) {
        worldCamera.setToOrtho(false, 10f * width / height, 10);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() { }



    public OrthographicCamera getWorldCamera() {
        return worldCamera;
    }
}
