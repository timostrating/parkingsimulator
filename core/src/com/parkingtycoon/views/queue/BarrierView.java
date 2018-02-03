package com.parkingtycoon.views.queue;

import com.parkingtycoon.helpers.IsometricConverter;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.CarQueueModel;
import com.parkingtycoon.views.AnimatedSpriteView;

public class BarrierView extends AnimatedSpriteView {

    public BarrierView() {
        super("sprites/barrier", true);
    }

    @Override
    public void start() {
        super.start();
        play("openAndClose", false, -1);
    }

    @Override
    public void updateView(BaseModel model) {

        super.updateView(model);

        if (model instanceof CarQueueModel) {

            if (currentAnimation != null)
                play("openAndClose", false);

            CarQueueModel queue = (CarQueueModel) model;
            spritePosition.set(queue.x, queue.y);
            IsometricConverter.normalToIsometric(spritePosition);

            sprite.setPosition(spritePosition.x - 2, spritePosition.y - 4);

            flip(queue.angle % 2 != 0, false);
        }

    }

    @Override
    public float renderIndex() {
        return super.renderIndex() - 3.5f;
    }
}
