package com.parkingtycoon.views;

import com.parkingtycoon.Game;

public class TestSpriteView extends AnimatedSpriteView {

    public TestSpriteView() {
        super("sprites/edward", null);

        play(Math.random() > .5f ? "run" : "idle", true);

        Game.getInstance().normalToIsometric(isometricPosition.set((float) Math.random() * 100, (float) Math.random() * 100));
    }

}
