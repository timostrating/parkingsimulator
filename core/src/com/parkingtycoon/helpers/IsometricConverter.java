package com.parkingtycoon.helpers;

import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.Game;

public class IsometricConverter {

    /**
     *
     * This helper class is used to
     *
     */

    public static Vector2 normalToIsometric(Vector2 convert) {

        float normalX = convert.x;
        float normalY = convert.y;

        convert.set(Game.WORLD_WIDTH * 2, Game.WORLD_HEIGHT);

        convert.x += normalX * 2;
        convert.y -= normalX;

        convert.x -= normalY * 2;
        convert.y -= normalY;

        return convert;
    }

    public static Vector2 isometricToNormal(Vector2 convert) {

        float isoX = convert.x;
        float isoY = convert.y - 1;

        float xDiff = isoX - Game.WORLD_WIDTH * 2;
        float yDiff = -2 * (isoY - Game.WORLD_HEIGHT);

        convert.x = (xDiff + yDiff) / 4;
        convert.y = (yDiff - xDiff) / 4;
        return convert;
    }
}
