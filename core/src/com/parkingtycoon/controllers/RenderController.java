package com.parkingtycoon.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.parkingtycoon.Game;
import com.parkingtycoon.helpers.Delegate;
import com.parkingtycoon.views.BaseView;

/**
 * This Class is responsible for enabeling View to be called every frame.
 */
public class RenderController extends BaseController {

    private Delegate<BaseView> views = new Delegate<BaseView>(true);
    private OrthographicCamera mainCamera;
    private Game game;

    private Delegate.Notifier<BaseView> preRenderer = BaseView::preRender;
    private Delegate.Notifier<BaseView> renderer;

    public RenderController(Game game) {
        super();
        this.game = game;
        renderer = object -> object.draw(game.batch);
    }

    public void preRender() {
        views.process(BaseView::start);         // for every new view -> call start()

        Gdx.gl.glClearColor(0, 0, 0, 1);        // clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sortViews();                            // sort the views so that the render-order is correct
        views.notifyObjects(preRenderer);
    }

    public void render() {
        game.batch.begin();
        views.notifyObjects(renderer);          // for every view -> call draw(game.batch)
        game.batch.end();
    }

    public void registerView(BaseView view) {
        views.register(view);
    }

    public void unregisterView(BaseView view) {
        views.unregister(view);
    }

    private void sortViews() {
//        for (int i = 1; i < views.size(); i++) {
//            BaseView temp = views.get(i);
//            float renderIndex = temp.renderIndex();
//
//            int j = i - 1;
//
//            while (j >= 0 && views.get(j).renderIndex() < renderIndex) {
//                views.set(j + 1, views.get(j));
//                j--;
//            }
//            views.set(j + 1, temp);
//        }
    }


    public OrthographicCamera getMainCamera() {
        return mainCamera;
    }

    public void setMainCamera(OrthographicCamera mainCamera) {
        this.mainCamera = mainCamera;
    }
}
