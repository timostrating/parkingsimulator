package com.parkingtycoon.views;

import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.helpers.IsometricConverter;

public class TestSpriteView extends AnimatedSpriteView {

    public TestSpriteView() {
        super("sprites/cars/pontiac", true);
    }

    @Override
    public void start() {
        super.start();
        play("spin", true);

        Vector2 pos = IsometricConverter.normalToIsometric(
                new Vector2((float) Math.random() * 100,
                        (float) Math.random() * 100)
        );
        sprite.setPosition(pos.x, pos.y);
    }


}
