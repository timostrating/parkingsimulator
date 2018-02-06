package com.parkingtycoon.models;

import com.parkingtycoon.helpers.ArrayNamed;
import com.parkingtycoon.helpers.interfaces.Named;

import java.util.ArrayList;

/**
 * This Class is responsible for storing all the animations and frames of a sprite
 *
 * @author Hilko Janssen
 */
public class AnimatedSpriteModel extends BaseModel implements Named {

    public ArrayNamed<Animation> animations = new ArrayNamed<>();
    public Animation.Frame[] frames;
    public int frameWidth, frameHeight;
    public float speedMultiplier;

    private String name;

    public AnimatedSpriteModel(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the sprite
     * @return The name of the sprite
     */
    @Override
    public String name() {
        return name;
    }

    /**
     * An animation consists of Frames
     */
    public static class Animation extends ArrayList<Animation.Frame> implements Named {

        /**
         * A frame is an image that is being shown for a certain duration before the next Frame will be showed
         */
        public static class Frame {
            public int x, y;
            public float duration;

            /**
             * This will create a new Frame
             * @param x        X-position of the TextureRegion
             * @param y        Y-position of the TextureRegion
             * @param duration How long should this frame be showed?
             */
            public Frame(int x, int y, float duration) {
                this.x = x;
                this.y = y;
                this.duration = duration;
            }

        }

        private String name;

        public Animation(String name) {
            this.name = name;
        }

        /**
         * Returns the name of the animation
         * @return The name of the animation
         */
        @Override
        public String name() {
            return name;
        }
    }

}