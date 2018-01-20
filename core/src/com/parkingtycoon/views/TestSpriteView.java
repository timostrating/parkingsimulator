package com.parkingtycoon.views;

import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.Game;

public class TestSpriteView extends AnimatedSpriteView {

    public TestSpriteView() {
        super("sprites/edward", true);

        play(Math.random() > .5f ? "run" : "idle", true);

        Vector2 pos = Game.getInstance().normalToIsometric(new Vector2((float) Math.random() * 100, (float) Math.random() * 100));
        sprite.setPosition(pos.x, pos.y);
    }

}
