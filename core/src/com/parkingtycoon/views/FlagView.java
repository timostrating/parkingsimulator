package com.parkingtycoon.views;

import com.parkingtycoon.helpers.IsometricConverter;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.FloorModel;

/**
 * Flags will give the player a good indication where new floorTiles will be placed.
 */
public class FlagView extends AnimatedSpriteView {

    private boolean green, initialized;

    public FlagView(boolean green) {
        super("sprites/flag", false);
        this.green = green;
    }

    @Override
    public void start() {
        super.start();
        play(green ? "green" : "red", true);
    }

    @Override
    public float renderIndex() {
        return -9999;
    }

    /**
     * Set position of the flag
     * @param model
     */
    @Override
    public void updateView(BaseModel model) {
        if (model instanceof FloorModel) {

            FloorModel floor = (FloorModel) model;
            int[] coords = green ? floor.getNewFloorFrom() : floor.getNewFloorTo();

            if (coords == null) {
                if (initialized) end();
                return;
            }

            spritePosition.set(coords[0], coords[1]);
            IsometricConverter.normalToIsometric(spritePosition);

            sprite.setPosition(spritePosition.x - .75f, spritePosition.y - 1.5f);

            initialized = true;
        }
    }
}
