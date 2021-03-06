package com.parkingtycoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.parkingtycoon.levels.MainMenuScreen;
import com.parkingtycoon.levels.ShaderScreen;
import com.parkingtycoon.levels.SimulationScreen;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;


/**
 * This Class is responsible for setting up the GameWindow.
 */
public class Game extends com.badlogic.gdx.Game {

    public static final int VIEWPORT_WIDTH = 700, VIEWPORT_HEIGHT = 500;
    public static final int WORLD_WIDTH = 100, WORLD_HEIGHT = 100;

    public static boolean inWorld(int x, int y) {
        return x >= 0 && x < Game.WORLD_WIDTH
                && y >= 0 && y < Game.WORLD_HEIGHT;
    }

    public InputMultiplexer inputMultiplexer;
    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    public BitmapFont font;

    private int currentScreenIndex = 0;


    // Screens should probably not be added while the simulation is running. But we keep everything Generic to optimize the compiler see https://stackoverflow.com/questions/13685158/why-does-java-allow-type-unsafe-array-assignments
    private ArrayList<Class<? extends Screen>> screens = new ArrayList<>();

    public Game() { // added Screens
        screens.add(MainMenuScreen.class);
        screens.add(SimulationScreen.class);
        screens.add(ShaderScreen.class);
    }

    @Override
    public void create() {
        inputMultiplexer = new InputMultiplexer();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        font = new BitmapFont();

        Gdx.input.setInputProcessor(inputMultiplexer);
        CompositionRoot.init(this);   // let's create a Composition root

        loadScreen(0);
    }

    public int getCurrentScreenIndex() {
        return currentScreenIndex;
    }

    public void loadNextScreen() {
        loadScreen(++currentScreenIndex);
    }

    public void loadScreen(int currentScreenIndex) {

        this.currentScreenIndex = currentScreenIndex;
        try {
            this.setScreen(screens.get(currentScreenIndex).getConstructor(Game.class).newInstance(this));
        } catch (InstantiationException | IllegalAccessException e) {
            System.out.println("Only classes that implement Screen are allowed.");
            e.printStackTrace();
        } catch (NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render() {
        super.render(); // important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        CompositionRoot.getInstance().simulationController.pause();
    }

}

