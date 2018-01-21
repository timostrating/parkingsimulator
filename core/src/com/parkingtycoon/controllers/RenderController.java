package com.parkingtycoon.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.parkingtycoon.Game;
import com.parkingtycoon.views.BaseView;

import java.util.ArrayList;

public class RenderController extends BaseController {

    private ArrayList<BaseView> views = new ArrayList<>();

    private OrthographicCamera mainCamera;
    private Game game;




    public RenderController(Game game) {
        super();
        this.game = game;
    }

    public void preRender() {
        Gdx.gl.glClearColor(0, 0, 0, 1);        // clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sortViews();                                                    // sort the views so that the render-order is correct
        for (BaseView v : views)
            v.preRender();                         // PreRender
    }

    public void render() {
        game.batch.begin();
        for (BaseView v : views)                // render views
            v.draw(game.batch);
        game.batch.end();
    }


    public void registerView(BaseView view) {
        views.add(view);
    }

    public void unregisterView(BaseView view) {
        views.remove(view);
    }


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


    public OrthographicCamera getMainCamera() {
        return mainCamera;
    }

    public void setMainCamera(OrthographicCamera mainCamera) {
        this.mainCamera = mainCamera;
    }
}
