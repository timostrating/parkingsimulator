package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTable;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;

/**
 * This class is responsible for setting up the UI.
 */
public class HudController implements Disposable {

    public boolean debug = false;

    private final CompositionRoot root;

    private float scale = 1f;
    private Stage stage;

    private OrthographicCamera hudCamera;
    private Viewport viewport;

    private SpriteBatch hudBatch;
    private VisTable mainTable;


    public HudController(SpriteBatch batch) {
        root = CompositionRoot.getInstance();
        hudCamera = new OrthographicCamera(Gdx.graphics.getWidth() * scale,  Gdx.graphics.getHeight() * scale);
        hudCamera.setToOrtho(false, Game.VIEWPORT_WIDTH, Game.VIEWPORT_HEIGHT);

        viewport = new ScreenViewport();
        stage = new Stage(viewport, batch);
        stage.getViewport().setCamera(hudCamera);

        hudBatch = new SpriteBatch();
        hudBatch.setProjectionMatrix(hudCamera.combined);

        VisUI.load();

        setup(stage);

        CompositionRoot.getInstance().game.inputMultiplexer.addProcessor(0, stage);

        root.inputController.onKeyDown.put(Input.Keys.F2, () -> {
            toggleDebug();
            return true;
        });
    }

    private void setup(Stage stage) {
        mainTable = new VisTable();
        mainTable.setDebug(debug);
        mainTable.setFillParent(true);

        mainTable.add(new HudOptionsController(stage).getTable()).expand().top().left();
        mainTable.add(new HudGameController(stage).getTable()).expand().top().right();
        mainTable.row();
        mainTable.add(new HudStatsController(stage).getTable()).bottom().left();
        mainTable.add(new HudTimeController(stage).getTable()).expand().bottom().right();

        stage.addActor(mainTable);
    }

    public void resize(int width, int height) {
        hudCamera.setToOrtho(false,  width, height);
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
        VisUI.dispose();
    }

    public void render() {
        hudCamera.update();

        hudBatch.begin();
        stage.act();
        stage.draw();
        hudBatch.end();
    }

    public void toggleDebug() {
        debug = !debug;
        mainTable.setDebug(debug);
    }
}
