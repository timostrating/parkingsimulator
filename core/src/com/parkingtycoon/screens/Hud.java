package com.parkingtycoon.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTable;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;
import com.parkingtycoon.controllers.InputController;
import com.parkingtycoon.controllers.ui.HudGameController;
import com.parkingtycoon.controllers.ui.HudOptionsController;
import com.parkingtycoon.controllers.ui.HudStatsController;
import com.parkingtycoon.controllers.ui.HudTimeController;

public class Hud implements Disposable {

    private final static boolean DEBUG = true;


    private final CompositionRoot root;

    private float scale = 1f;
    //    private Texture myTexture;
    private Stage stage;
    private Skin skin;

    private OrthographicCamera hudCamera;
    private Viewport viewport;

    private SpriteBatch hudBatch;
    private ShapeRenderer shapeRenderer;


    public Hud(SpriteBatch batch) {
        shapeRenderer = new ShapeRenderer();

        root = CompositionRoot.getInstance();
        hudCamera = new OrthographicCamera(Gdx.graphics.getWidth() * scale,  Gdx.graphics.getHeight() * scale);
        hudCamera.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);

        viewport = new ScreenViewport();
        stage = new Stage(viewport, batch);
        stage.getViewport().setCamera(hudCamera);

        hudBatch = new SpriteBatch();
        hudBatch.setProjectionMatrix(hudCamera.combined);

        VisUI.load();

                addStuff(stage);

        Gdx.input.setInputProcessor(new InputMultiplexer(new InputController(), stage));  // TODO: the Game class may should do this.
    }

    private void addStuff(Stage stage) {  // TODO: RENAME
        VisTable mainTable = new VisTable();
        mainTable.setDebug(DEBUG);
        mainTable.setFillParent(true);

        mainTable.add(new HudOptionsController().getTable()).expand().top().left();
        mainTable.add(new HudGameController().getTable()).expand().top().right();
        mainTable.row();
        mainTable.add(new HudStatsController().getTable()).expand().bottom().left();
        mainTable.add(new HudTimeController().getTable()).expand().bottom().right();

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
}
