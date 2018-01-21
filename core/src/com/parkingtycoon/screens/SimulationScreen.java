package com.parkingtycoon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;
import com.parkingtycoon.views.BaseView;

import java.util.ArrayList;

public class SimulationScreen implements Screen {

    private ArrayList<BaseView> views = new ArrayList<>();

    final Game game;
    private CompositionRoot root;

    private OrthographicCamera worldCamera;
    private Hud hud;


    public SimulationScreen(Game game) {
        this.game = game;
        worldCamera = new OrthographicCamera();
//        worldCamera.setToOrtho(false, 800, 480);
        root = CompositionRoot.getInstance();
        hud = new Hud(game.batch);


    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        CompositionRoot.getInstance().simulationController.update();    // update simulation

        Gdx.gl.glClearColor(0, 0, 0, 1);        // clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sortViews();                                                    // sort the views so that the render-order is correct

        for (BaseView v : views)
            v.preRender();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined); // render Hud
        hud.stage.draw();

        game.batch.begin();

            for (BaseView v : views)                                        // draw
                v.draw(game.batch);

        game.batch.end();

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


//    public void registerView(BaseView view) {
//        views.add(view);
//    }
//
//    public void unregisterView(BaseView view) {
//        views.remove(view);
//    }

    private void sortViews() {
        for (int i = 1; i < views.size(); i++) {
            BaseView temp = views.get(i);
            float renderIndex = temp.renderIndex();

            int j = i - 1;

            while (j >= 0 && views.get(j).renderIndex() < renderIndex) {
                views.set(j + 1, views.get(j));
                j--;
            }
            views.set(j + 1, temp);
        }
    }
}
