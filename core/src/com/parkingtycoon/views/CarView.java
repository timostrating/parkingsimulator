package com.parkingtycoon.views;

import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.helpers.IsometricConverter;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.CarModel;

/**
 * This Class is responsible for showing an animated sprite of a drivable car.
 */
public class CarView extends AnimatedSpriteView {

    private Vector2 spritePosition = new Vector2();


    public CarView() {
        super("sprites/cars/pontiac", true);
    }

    @Override
    public void start() {
        super.start();
        play("spin", true);
    }

    @Override
    public void updateView(BaseModel model) {

        if (model instanceof CarModel) {
            CarModel car = (CarModel) model;

            spritePosition.set(car.position);
            IsometricConverter.normalToIsometric(spritePosition);

        }

    }
}
