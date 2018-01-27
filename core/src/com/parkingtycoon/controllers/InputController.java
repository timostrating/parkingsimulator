package com.parkingtycoon.controllers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This Class is responsible for processing user input like keyboard and mouse input that are applied to the GameWindow
 */
public class InputController extends BaseController implements InputProcessor {

    public ArrayList<MouseListener> onMouseButtonDown = new ArrayList<>();
    public ArrayList<MouseListener> onMouseButtonUp = new ArrayList<>();

    public HashMap<Integer, KeyListener> onKeyDown = new HashMap<>();
    public HashMap<Integer, KeyListener> onKeyUp = new HashMap<>();

    @FunctionalInterface
    public interface MouseListener {
        boolean action(int screenX, int screenY, int button);
    }

    @FunctionalInterface
    public interface KeyListener {
        boolean action();
    }

    public InputController(Game game) {
        game.inputMultiplexer.addProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
        for (Map.Entry<Integer, KeyListener> listenerEntry : onKeyDown.entrySet())
            if (listenerEntry.getKey() == keycode && listenerEntry.getValue().action())
                return true;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        for (Map.Entry<Integer, KeyListener> listenerEntry : onKeyUp.entrySet())
            if (listenerEntry.getKey() == keycode && listenerEntry.getValue().action())
                return true;
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (MouseListener mouseListener : onMouseButtonDown)
            if (mouseListener.action(screenX, screenY, button))
                return true;

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (MouseListener mouseListener : onMouseButtonUp)
            if (mouseListener.action(screenX, screenY, button))
                return true;

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        OrthographicCamera camera = CompositionRoot.getInstance().renderController.getMainCamera();
        camera.zoom = Math.max(1, Math.min(18, camera.zoom + amount * camera.zoom / 16f));

        return false;
    }
}
