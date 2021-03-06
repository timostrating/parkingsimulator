package com.parkingtycoon.views.elevator;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.IsometricConverter;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.ElevatorModel;
import com.parkingtycoon.views.AnimatedSpriteView;

/**
 * Shows the animated sprite of an elevator with opening and closing doors
 */
public class ElevatorView extends AnimatedSpriteView {

    private float doorsTimer;
    private int currentFloor;
    private boolean initialized;

    public ElevatorView() {

        super("sprites/elevator", true);

    }

    @Override
    public void start() {
        super.start();
    }

    /**
     * This method will set the frame of the sprite, according to the doorsTimer
     */
    @Override
    public void preRender() {
        int frame = currentFloor == CompositionRoot.getInstance().floorsController.getCurrentFloor() ?
                (int) ((1 - doorsTimer) * (spriteModel.frames.length - 1))
                : spriteModel.frames.length - 1;
        setRegion(spriteModel.frames[frame]);
    }

    @Override
    public float renderIndex() {
        return super.renderIndex() - 3.5f;
    }

    /**
     * This method will set position and rotation of the elevator view
     * @param model
     */
    @Override
    public void updateView(BaseModel model) {
        super.updateView(model);

        if (model instanceof ElevatorModel) {

            ElevatorModel elevator = (ElevatorModel) model;

            doorsTimer = elevator.getDoorsTimer();
            currentFloor = elevator.currentFloor;

            if (!initialized) {
                spritePosition.set(elevator.x + 1.5f, elevator.y + 3);
                IsometricConverter.normalToIsometric(spritePosition);
                sprite.setPosition(spritePosition.x, spritePosition.y);

                flip(elevator.angle == 0, false);
                initialized = true;
            }

        }

    }
}
