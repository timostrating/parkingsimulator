package com.parkingtycoon.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;


/**
 * This Class provides help to the Controllers and Views who are working with isometric grid positions.
 * An example would be the isometric map.
 */
public class IsometricConverter {

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
        float isoY = convert.y;

        float xDiff = isoX - Game.WORLD_WIDTH * 2;
        float yDiff = -2 * (isoY - Game.WORLD_HEIGHT);

        convert.x = (xDiff + yDiff) / 4;
        convert.y = (yDiff - xDiff) / 4;
        return convert;
    }

    public static Vector2 cursorToNormal() {
        Vector3 isometric = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        CompositionRoot.getInstance().renderController.getMainCamera().unproject(isometric);

        return isometricToNormal(new Vector2(isometric.x, isometric.y));
    }

}
