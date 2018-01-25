package com.parkingtycoon.models;

import com.parkingtycoon.helpers.ArrayNamed;
import com.parkingtycoon.helpers.interfaces.Named;

import java.util.ArrayList;

/**
 * This Class is responsible storing the animation frames for animated sprites.
 */
public class AnimatedSpriteModel extends BaseModel implements Named {

    public ArrayNamed<Animation> animations = new ArrayNamed<>();
    public int frameWidth, frameHeight;
    public float speedMultiplier;

    private String name;

    public AnimatedSpriteModel(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    public static class Animation extends ArrayList<Animation.Frame> implements Named {

        public static class Frame {
            public int x, y;
            public float duration;

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

        @Override
        public String name() {
            return name;
        }
    }

}