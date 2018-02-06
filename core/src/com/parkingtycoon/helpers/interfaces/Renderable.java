package com.parkingtycoon.helpers.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * This Class is responsible for providing a basic interface for items who would like to be rendered.
 *
 * @author GGG
 */
public interface Renderable {

    /**
     * Before first frame is drawn.
     */
    void start();

    /**
     * Every frame directly after the screen is cleared.
     */
    void preRender();

    /**
     * Render the frame
     *
     * @param batch the sprite bach where you render on.
     */
    void render(SpriteBatch batch);

}
