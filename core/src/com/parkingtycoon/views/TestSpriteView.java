package com.parkingtycoon.views;

public class TestSpriteView extends AnimatedSpriteView {

    public TestSpriteView() {
        super("sprites/edward", true);

        play(Math.random() > .5f ? "run" : "idle", true);

//        Vector2 pos = Game.getInstance().normalToIsometric(new Vector2((float) Math.random() * 100, (float) Math.random() * 100));
//        sprite.setPosition(pos.x, pos.y);
    }

}
