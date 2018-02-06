package com.parkingtycoon.views.queue;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.helpers.IsometricConverter;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.CarQueueModel;
import com.parkingtycoon.views.AnimatedSpriteView;

/**
 * This Class is responsible for showing a big sign above an entrance or exit
 */
public class QueueSignView extends AnimatedSpriteView {

    private Vector2 normalPos = new Vector2();
    private String type;

    public QueueSignView(String type) {
        super("sprites/queueSigns", true);
        this.type = type;
    }

    @Override
    public void start() {
        super.start();
        play(type, true);
        sprite.setPosition(spritePosition.x - 2.6f, spritePosition.y - 4.3f);
    }

    @Override
    public float renderIndex() {
        return super.renderIndex() - 3;
    }

    @Override
    public void updateView(BaseModel model) {

        super.updateView(model);

        if (model instanceof CarQueueModel) {

            CarQueueModel queue = (CarQueueModel) model;
            normalPos.set(queue.x, queue.y);

            spritePosition.set(queue.x, queue.y);
            IsometricConverter.normalToIsometric(spritePosition);
        }
    }

    @Override
    public void debugRender(ShapeRenderer shapeRenderer) {

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.line(normalPos.x, normalPos.y, normalPos.x + 1, normalPos.y + 1);

    }
}
