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
 *
 * @author GGG
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

    public boolean debug = false;  // should the debug rendering be activated


    /**
     * This is the standard constructor for the render controller
     *
     * @param game we need data from the game class. Also nobody else but a direct involved class from Game should create us.
     */
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

    /**
     * Setup a new Render
     */
    public void preRender() {
        views.process(starter);                 // for every new view -> call start()

        Gdx.gl.glClearColor(0, 0, 0, 1);        // clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        views.sort(sorter);                     // sort the views so that the render-order is correct
        views.notifyObjects(preRenderer);       // for every view -> call preRender()
    }

    /**
     * Render the screen
     */
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

    /**
     * Register a view as to be rendered
     *
     * @param view the view that would like to be rendered.
     */
    public void registerView(BaseView view) {
        views.register(view);
    }

    /**
     * Unregister a view so that it wil not be rendered anymore.
     *
     * @param view this is the view that would like to stop listening to what this class has to scream.
     */
    public void unregisterView(BaseView view) {
        views.unregister(view);
    }

    /**
     * Getter fot the current active camera
     *
     * @return the current active camera
     */
    public OrthographicCamera getMainCamera() {
        return mainCamera;
    }

    /**
     * Setter for a new currently active camera.
     *
     * @param mainCamera the camera that you would like to main the new main camera.
     */
    public void setMainCamera(OrthographicCamera mainCamera) {
        this.mainCamera = mainCamera;
    }
}
