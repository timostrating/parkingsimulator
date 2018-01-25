package com.parkingtycoon.helpers.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * This Class is responsible for providing a basic interface for items who would like to be rendered.
 */
public interface Renderable {

    void start();                       // before first frame is drawn
    void preRender();                   // every frame directly after the screen is cleared
    void draw(SpriteBatch batch);       // draw the frame

}
