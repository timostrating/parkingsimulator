package com.parkingtycoon.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.parkingtycoon.CompositionRoot;


/**
 * This Class is responsible for keyboard and mouse controllers that are applied to the GameWindow
 */
public class InputController implements com.badlogic.gdx.InputProcessor {

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE)
            Gdx.app.exit();

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.F3) {
            RenderController renderController = CompositionRoot.getInstance().renderController;
            renderController.debug = !renderController.debug;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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
        camera.zoom = Math.max(1, Math.min(10, camera.zoom + amount * .2f));
        return true;
    }
}
