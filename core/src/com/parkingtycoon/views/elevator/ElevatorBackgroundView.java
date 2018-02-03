package com.parkingtycoon.views.elevator;

import com.parkingtycoon.helpers.IsometricConverter;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.ElevatorModel;
import com.parkingtycoon.views.SpriteView;

public class ElevatorBackgroundView extends SpriteView {

    private boolean initialized;

    public ElevatorBackgroundView() {
        super("sprites/elevatorBackground.png");
    }

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
