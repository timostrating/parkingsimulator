package com.parkingtycoon.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;
import com.parkingtycoon.helpers.Delegate;
import com.parkingtycoon.views.BaseView;

/**
 * This Class is responsible for enabling Views to be called every frame.
 */
public class RenderController extends BaseController {

    private Delegate<BaseView> views = new Delegate<BaseView>(true);
    private OrthographicCamera mainCamera;
    private Game game;

    private Delegate.Notifier<BaseView> preRenderer = BaseView::preRender;
    private Delegate.Notifier<BaseView> renderer;
    private Delegate.Notifier<BaseView> shapeRenderer;
    private Delegate.Notifier<BaseView> debugRenderer;
    private Delegate.Sorter<BaseView> sorter = BaseView::renderIndex;
    private Delegate.Starter<BaseView> starter = BaseView::start;

    public boolean debug = false;


    public RenderController(Game game) {
        super();
        this.game = game;
        renderer = object -> object.render(game.batch);
        shapeRenderer = object -> object.renderShapes(game.shapeRenderer);
        debugRenderer = object -> object.debugRender(game.shapeRenderer);

        CompositionRoot.getInstance().inputController.onKeyDown.put(Input.Keys.F3, () -> {
            debug = !debug;
            return true;
        });
    }

    public void preRender() {
        views.process(starter);                 // for every new view -> call start()

        Gdx.gl.glClearColor(0, 0, 0, 1);        // clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        views.sort(sorter);                     // sort the views so that the render-order is correct
        views.notifyObjects(preRenderer);       // for every view -> call preRender()
    }

    public void render() {
        game.batch.begin();
        views.notifyObjects(renderer);          // for every view -> call render(game.batch)
        game.batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        views.notifyObjects(shapeRenderer);
        game.shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        if (debug) {
            Gdx.gl.glLineWidth(3);
            game.shapeRenderer.begin();
            views.notifyObjects(debugRenderer);
            game.shapeRenderer.end();
        }
    }

    public void registerView(BaseView view) {
        views.register(view);
    }

    public void unregisterView(BaseView view) {
        views.unregister(view);
    }

    public OrthographicCamera getMainCamera() {
        return mainCamera;
    }

    public void setMainCamera(OrthographicCamera mainCamera) {
        this.mainCamera = mainCamera;
    }
}
