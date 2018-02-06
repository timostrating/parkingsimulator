package com.parkingtycoon.views.elevator;

import com.parkingtycoon.helpers.IsometricConverter;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.ElevatorModel;
import com.parkingtycoon.views.SpriteView;

/**
 * Shows the background of an elevator
 */
public class ElevatorBackgroundView extends SpriteView {

    private boolean initialized;

    public ElevatorBackgroundView() {
        super("sprites/elevatorBackground.png");
    }

    /**
     * Sets the position and rotation of the background according to the elevators position
     * @param model Model
     */
    @Override
    public void updateView(BaseModel model) {
        super.updateView(model);

        if (model instanceof ElevatorModel && !initialized) {
            ElevatorModel elevator = (ElevatorModel) model;
            spritePosition.set(elevator.x + 1.5f, elevator.y + 3);
            IsometricConverter.normalToIsometric(spritePosition);
            sprite.setPosition(spritePosition.x, spritePosition.y);
            sprite.flip(elevator.angle == 0, false);
            initialized = true;
        }
    }
}
