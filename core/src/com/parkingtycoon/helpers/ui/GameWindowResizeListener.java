package com.parkingtycoon.helpers.ui;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

/**
 * This is a helper provided by VisUI
 *
 * @author Timo Strating
 */
public abstract class GameWindowResizeListener implements EventListener {
    @Override
    public boolean handle (Event event) {
        if (event instanceof GameWindowResizeEvent == false) return false;
        resize();
        return false;
    }

    public abstract void resize ();
}

