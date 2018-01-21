package com.parkingtycoon.views;

import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.CompositionRoot;

public class TestSpriteView extends AnimatedSpriteView {

    public TestSpriteView() {
        super("sprites/edward", true);

        play(Math.random() > .5f ? "run" : "idle", true);

        Vector2 pos = normalToIsometric(new Vector2((float) Math.random() * 100, (float) Math.random() * 100));
        sprite.setPosition(pos.x, pos.y);
    }

    public Vector2 normalToIsometric(Vector2 convert) { // HACK

        float normalX = convert.x;
        float normalY = convert.y;

        convert.set(CompositionRoot.getInstance().floorsController.getWidth() * 2, CompositionRoot.getInstance().floorsController.getHeight()); // HACK

        convert.x += normalX * 2;
        convert.y -= normalX;

        convert.x -= normalY * 2;
        convert.y -= normalY;

        return convert;
    }

    public Vector2 isometricToNormal(Vector2 convert) { // HACK

        float isoX = convert.x;
        float isoY = convert.y;

        float xDiff = isoX - CompositionRoot.getInstance().floorsController.getWidth() * 2;  // HACK
        float yDiff = -2 * (isoY - CompositionRoot.getInstance().floorsController.getHeight());  // HACK

        convert.x = (xDiff + yDiff) / 4;
        convert.y = (yDiff - xDiff) / 4;
        return convert;
    }
}
