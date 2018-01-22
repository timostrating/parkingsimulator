package com.parkingtycoon.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.kotcrab.vis.ui.widget.VisTable;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;
import com.parkingtycoon.controllers.InputController;
import com.parkingtycoon.views.ui.TestListView;

public class Hud implements Disposable {

    public final static boolean DEBUG = true;


    private final CompositionRoot root;

    private VisTable table;
    private float scale = 1f;
    //    private Texture myTexture;
    private Stage stage;
    private Skin skin;

    private OrthographicCamera hudCamera;
    private Viewport viewport;

    private SpriteBatch hudBatch;



    public Hud(SpriteBatch batch) {
        root = CompositionRoot.getInstance();
        hudCamera = new OrthographicCamera(Gdx.graphics.getWidth() * scale,  Gdx.graphics.getHeight() * scale);
        hudCamera.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);

        viewport = new ScreenViewport();
        stage = new Stage(viewport, batch);
        stage.getViewport().setCamera(hudCamera);

        VisUI.load();

        table = new VisTable();
        table.setDebug(DEBUG);
        table.setFillParent(true);

        hudBatch = new SpriteBatch();
        hudBatch.setProjectionMatrix(hudCamera.combined);
//        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        Texture buttonTexture = new Texture(Gdx.files.internal("badlogic.jpg"));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(buttonTexture));

        final VisImageButton button = new VisImageButton(drawable);
        button.setWidth(100);
        button.setHeight(100);

        final VisImageButton button2 = new VisImageButton(drawable);
        button.setWidth(100);
        button.setHeight(100);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                root.simulationController.togglePaused();
            }
        });

        button2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addActor(new TestListView());
            }
        });

        table.add(button).expand().top().left();
        table.add(button2).expand().top().right();
        stage.addActor(table);

        Gdx.input.setInputProcessor(new InputMultiplexer(new InputController(), stage));  // TODO: the Game class may should do this.
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
