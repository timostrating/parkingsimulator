package com.parkingtycoon.views;

import com.badlogic.gdx.math.Vector2;

public class TestSpriteView extends AnimatedSpriteView {

    public TestSpriteView() {
        super("sprites/cars/pontiac", true);

        play("spin", true);

        Vector2 pos = normalToIsometric(new Vector2((float) Math.random() * 100, (float) Math.random() * 100));
        sprite.setPosition(pos.x, pos.y);
    }

}
