package com.parkingtycoon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;
import com.parkingtycoon.views.TestSpriteView;

public class SimulationScreen implements Screen {


    final Game game;
    private CompositionRoot root;

    private OrthographicCamera worldCamera;
    private Hud hud;


    public SimulationScreen(Game game) {
        this.game = game;

        worldCamera = new OrthographicCamera();
        worldCamera.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);

        root = CompositionRoot.getInstance();
        root.renderController.setMainCamera(worldCamera);

        hud = new Hud(game.batch);

        for (int i = 0; i < 20000; i++)
            new TestSpriteView();
    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {

        game.batch.setProjectionMatrix(worldCamera.combined);
        root.simulationController.update();     // update simulation
        root.renderController.preRender();      // preRender views
        root.renderController.render();         // render views
        hud.render();                           // render UI

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
    public void dispose() {
        hud.dispose();
    }



    public OrthographicCamera getWorldCamera() {
        return worldCamera;
    }
}
