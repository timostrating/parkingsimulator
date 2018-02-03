package com.parkingtycoon.views.queue;

import com.parkingtycoon.helpers.IsometricConverter;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.CarQueueModel;
import com.parkingtycoon.views.SpriteView;

public class ArrowView extends SpriteView {

    private int angle;

    public ArrowView() {
        super("sprites/barrierArrow.png");
    }

    @Override
    public void start() {
        super.start();

        sprite.flip(angle == 2 || angle == 3, angle == 1 || angle == 2);
        sprite.setOriginCenter();
        sprite.setOriginBasedPosition(spritePosition.x, spritePosition.y);

    }

    @Override
    public float renderIndex() {
        return 99999;
    }

    @Override
    public void updateView(BaseModel model) {

        super.updateView(model);

        if (model instanceof CarQueueModel) {

            CarQueueModel queue = (CarQueueModel) model;
            spritePosition.set(queue.x + 1.5f, queue.y + 1.5f);
            IsometricConverter.normalToIsometric(spritePosition);
            angle = queue.angle;

        }
    }
}
