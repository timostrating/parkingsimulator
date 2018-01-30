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
public class QueueSignView extends AnimatedSpriteView {

    private Vector2 normalPos = new Vector2();
    private String type;
    private int angle;

    public QueueSignView(String type) {
        super("sprites/queueSigns", true);
        this.type = type;
    }

    @Override
    public void start() {
        super.start();
        play(type, true);
    }

    @Override
    public void updateView(BaseModel model) {

        if (model instanceof CarQueueModel) {

            CarQueueModel queue = (CarQueueModel) model;
            normalPos.set(queue.x, queue.y);

            spritePosition.set(queue.x, queue.y);
            IsometricConverter.normalToIsometric(spritePosition);

            sprite.setPosition(spritePosition.x - 2, spritePosition.y - 4);
        }
    }

    @Override
    public void debugRender(ShapeRenderer shapeRenderer) {

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.line(normalPos.x, normalPos.y, normalPos.x + 1, normalPos.y + 1);

    }
}
