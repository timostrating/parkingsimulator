package com.parkingtycoon.views;

import com.parkingtycoon.models.BaseModel;

public class EntranceView extends AnimatedSpriteView {

    public EntranceView() {
        super("sprites/entrance", true);
    }

    @Override
    public void start() {
        super.start();
        play("openAndClose", true);
        sprite.setPosition(10, 0);
    }

    @Override
    public void updateView(BaseModel model) {

    }
}
