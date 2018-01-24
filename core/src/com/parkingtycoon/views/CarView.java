package com.parkingtycoon.views;

import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.helpers.IsometricConverter;
import com.parkingtycoon.helpers.Logger;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.CarModel;

public class CarView extends AnimatedSpriteView {

    private Vector2 spritePosition = new Vector2();
    private Vector2 lastDirection = new Vector2();
    private int lastFrame = -1;

    public CarView() {
        super("sprites/cars/pontiac", true);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void updateView(BaseModel model) {


        if (model instanceof CarModel) {

            CarModel car = (CarModel) model;

            spritePosition.set(car.position);
            IsometricConverter.normalToIsometric(spritePosition);
            sprite.setPosition(spritePosition.x, spritePosition.y);

            if (spriteModel != null && !lastDirection.equals(car.direction)) {

                lastDirection.set(car.direction);

                int frame = 33 - (int) ((car.direction.angle() / 360.01f) * 33) - 1;
                Logger.info(car.direction + " " + car.direction.angle() + " frame = " + frame);
                if (lastFrame != -1 && frame < lastFrame)
                    frame = lastFrame - 1;
                else if (lastFrame != -1 && frame > lastFrame)
                    frame = lastFrame + 1;

                setRegion(spriteModel.frames[frame]);
                lastFrame = frame;
            }

        }

    }
}
