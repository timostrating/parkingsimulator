package com.parkingtycoon.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.helpers.IsometricConverter;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.CarQueueModel;

/**
 * This Class is responsible for showing an entrance.
 */
public class EntranceView extends AnimatedSpriteView {

    private Vector2 normalPos = new Vector2();

    public EntranceView() {
        super("sprites/entrance", true);
    }

    @Override
    public void start() {
        super.start();
        play("test", true);
    }

    @Override
    public void updateView(BaseModel model) {

        if (model instanceof CarQueueModel) {

            CarQueueModel entrance = (CarQueueModel) model;
            normalPos.set(entrance.x, entrance.y);

            spritePosition.set(entrance.x, entrance.y);
            IsometricConverter.normalToIsometric(spritePosition);

            sprite.setPosition(spritePosition.x, spritePosition.y);

        }

    }

    @Override
    public void debugRender(ShapeRenderer shapeRenderer) {

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.line(normalPos.x, normalPos.y, normalPos.x + 1, normalPos.y + 1);

    }
}
