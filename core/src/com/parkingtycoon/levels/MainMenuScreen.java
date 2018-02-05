package com.parkingtycoon.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;
import com.parkingtycoon.helpers.Logger;


/**
 * This Class is responsible for setting up the Main Menu level.
 */
public class MainMenuScreen implements Screen {

    private final Game game;
    private final CompositionRoot root;

    private OrthographicCamera camera;


    public MainMenuScreen(final Game game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Game.VIEWPORT_WIDTH, Game.VIEWPORT_HEIGHT);
        root = CompositionRoot.getInstance();

        root.renderController.setMainCamera(camera);
    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to ParkingTycoon!!! ", 100, 150);
        game.font.draw(game.batch, "Click anywhere to begin!", 100, 100);
        game.batch.end();

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

            if (!Gdx.files.local("saves/").isDirectory()) {
                Logger.info("First time playing Parking Simulator Tycoon.\nsaves folder with demo will be generated");

                FileHandle output = Gdx.files.local("saves/Demo.parkingsimulatortycoon");
                output.writeString(Gdx.files.internal("saves/Demo.parkingsimulatortycoon").readString(), false);
            }

            FileHandle[] files = Gdx.files.local("saves/").list(); // these are all the save files
            for (FileHandle file : files) {
                Logger.info(file.toString());
            }

            CompositionRoot.getInstance().floorsController.fromJson("saves/Demo.parkingsimulatortycoon");

            game.setScreen(new SimulationScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() { }
}
