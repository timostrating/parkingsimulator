package com.parkingtycoon;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.parkingtycoon.screens.MainMenuScreen;
import com.parkingtycoon.screens.SimulationScreen;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Game extends com.badlogic.gdx.Game {

    public static final int V_WIDTH = 500;
    public static final int V_HEIGHT = 500;

    public SpriteBatch batch;
    public BitmapFont font;


    private int currentScreenIndex = 0;

    // Screens should probably not be added while the simulation is running. But we keep everything Generic too optimize the compiler
    private ArrayList<Class<? extends Screen>> screens = new ArrayList<>();  // see  https://stackoverflow.com/questions/13685158/why-does-java-allow-type-unsafe-array-assignments



    public Game() { // added Screens
        screens.add(MainMenuScreen.class);
        screens.add(SimulationScreen.class);
    }

    @Override
	public void create () {

        CompositionRoot.init(this);   // let's create a Composition root

        batch = new SpriteBatch();
        font = new BitmapFont();
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

