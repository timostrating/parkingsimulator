package com.parkingtycoon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;
import com.parkingtycoon.controllers.SimulationController;
import com.parkingtycoon.views.EntranceView;

public class SimulationScreen implements Screen {


    final Game game;
    private CompositionRoot root;

    private OrthographicCamera worldCamera;
    private Hud hud;


    public SimulationScreen(Game game) {

        root = CompositionRoot.getInstance();
        root.simulationController.startSimulation();

        this.game = game;

        worldCamera = new OrthographicCamera();
        worldCamera.setToOrtho(false, Game.VIEWPORT_WIDTH, Game.VIEWPORT_HEIGHT);

        root = CompositionRoot.getInstance();
        root.renderController.setMainCamera(worldCamera);

        hud = new Hud(game.batch);

//        for (int i = 0; i < 1000; i++)
//            new TestSpriteView();

        new EntranceView();
    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {

        root.renderController.preRender();      // preRender views
        game.batch.setProjectionMatrix(worldCamera.combined);
        game.shapeRenderer.setProjectionMatrix(worldCamera.combined);
        root.renderController.render();         // render views
        hud.render();                           // render UI

        SimulationController simulationController = CompositionRoot.getInstance().simulationController;

        Gdx.graphics.setTitle("Parking Tycoon (fps: " + Gdx.graphics.getFramesPerSecond() + ") (updates/sec: " + simulationController.getUpdatesPerSecond() + ") (real updates/sec: " + simulationController.realUpdatesPerSecond + ")");
    }

    @Override
    public void resize(int width, int height) {
        worldCamera.setToOrtho(false, 10f * width / height, 10);
        hud.resize(width, height);
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
