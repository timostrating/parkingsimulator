package com.parkingtycoon;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.parkingtycoon.levels.MainMenuScreen;
import com.parkingtycoon.levels.SimulationScreen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;


/**
 * This Class is responsible for setting up the GameWindow.
 */
public class Game extends com.badlogic.gdx.Game {

    public static final int VIEWPORT_WIDTH = 700, VIEWPORT_HEIGHT = 500;
    public static final int WORLD_WIDTH = 100, WORLD_HEIGHT = 100;
  
    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    public BitmapFont font;

    private int currentScreenIndex = 0;


    // Screens should probably not be added while the simulation is running. But we keep everything Generic to optimize the compiler see https://stackoverflow.com/questions/13685158/why-does-java-allow-type-unsafe-array-assignments
    private ArrayList<Class<? extends Screen>> screens = new ArrayList<>();

    public Game() { // added Screens
        screens.add(MainMenuScreen.class);
        screens.add(SimulationScreen.class);
    }

    @Override
	  public void create () {

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        font = new BitmapFont();

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
            this.setScreen((Screen) (screens.get(currentScreenIndex).getConstructor(Game.class).newInstance(this)));
        } catch (InstantiationException | IllegalAccessException e) {
            System.out.println("Only classes who implement Screen are allowed.");
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
    }

}

