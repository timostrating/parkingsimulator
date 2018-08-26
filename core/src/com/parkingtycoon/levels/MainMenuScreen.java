package com.parkingtycoon.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;
import com.parkingtycoon.helpers.Logger;
import com.parkingtycoon.helpers.TextureHelper;
import com.parkingtycoon.helpers.interfaces.ClickListener;


/**
 * This Class is responsible for setting up the Main Menu level.
 *
 * @author Timo Strating
 */
public class MainMenuScreen implements Screen {

    private final Game game;
    private final CompositionRoot root;

    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private SpriteBatch hudBatch;

    /**
     * Setup the main menu
     *
     * @param game we require data from the game, also probably should only the game create us.
     */
    public MainMenuScreen(final Game game) {
        this.game = game;
        root = CompositionRoot.getInstance();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Game.VIEWPORT_WIDTH, Game.VIEWPORT_HEIGHT);

        viewport = new ScreenViewport();
        stage = new Stage(viewport, game.batch);
        stage.getViewport().setCamera(camera);

        hudBatch = new SpriteBatch();
        hudBatch.setProjectionMatrix(camera.combined);

        root.renderController.setMainCamera(camera);

        create();
    }

    /**
     * Create the menu
     */
    private void create() {
        root.game.inputMultiplexer.addProcessor(stage);

        VisTable mainTable = new VisTable(true);
        mainTable.setFillParent(true);

        Image image = new Image(TextureHelper.setupDrawable("ui/logo.png", 1000, 600));
        mainTable.add(image);
        mainTable.row();


        VisTable table = new VisTable(true);

        final VisTextButton playButton = new VisTextButton("PLAY");
        table.add(playButton).padRight(20).padLeft(20);

        playButton.addListener((ClickListener) (event, actor) -> { game.setScreen(new SimulationScreen(game)); dispose();} );


        final VisTextButton loadButton = new VisTextButton("LOAD");
        table.add(loadButton).padRight(20).padLeft(20);

        loadButton.addListener((ClickListener) (event, actor) -> { stage.addActor(new LoadWindow()); } );


        final VisTextButton exitButton = new VisTextButton("EXIT");
        table.add(exitButton).padRight(20).padLeft(20);

        exitButton.addListener((ClickListener) (event, actor) -> Gdx.app.exit());

        mainTable.add(table);
        stage.addActor(mainTable);

    }

    @Override
    public void show() { }

    /**
     * render 60 frames per second
     *
     * @param delta automatic value
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        hudBatch.setProjectionMatrix(camera.combined);

        hudBatch.begin();
        stage.act();
        stage.draw();
        hudBatch.end();
    }

    /**
     * resize event fired every time the game window is resized.
     *
     * @param width the actual width of the window.
     * @param height the actual height of the window.
     */
    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        viewport.update(width, height);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() { }


    /**
     * This class is responsible for showing a window where the user can select his save game.
     */
    private class LoadWindow extends VisWindow {

        /**
         * The standard constructor of the window.
         */
        public LoadWindow() {
            super("load");
            columnDefaults(0).left();
            addCloseButton();

            setSize(400, 300);
            setPosition(234, 50);


            VisTable table = new VisTable(true);

            if (!Gdx.files.local("saves/").isDirectory()) {
                Logger.info("First time playing Parking Simulator Tycoon.\nsaves folder with demo will be generated");

                FileHandle output = Gdx.files.local("saves/Demo.parkingsimulatortycoon");
                output.writeString(Gdx.files.internal("saves/Demo.parkingsimulatortycoon").readString(), false);
            }

            FileHandle[] files = Gdx.files.local("saves/").list(); // these are all the save files
            for (FileHandle file : files) {
                Logger.info(file.toString());
                VisTextButton button = new VisTextButton(file.toString().replace("saves/", ""));
                button.addListener((ClickListener) (event, actor) -> {
                    CompositionRoot.getInstance().floorsController.fromJson(file.toString());
                    game.setScreen(new SimulationScreen(game));
                });
                table.row();
                table.add(button).growX().left().top();
            }

            add(table).left();
        }
    }

}