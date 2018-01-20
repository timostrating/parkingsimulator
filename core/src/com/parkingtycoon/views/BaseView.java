package com.parkingtycoon.views;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.parkingtycoon.Game;
import com.parkingtycoon.models.BaseModel;

/**
 * Created by Hilko on 18-1-2018.
 */
public abstract class BaseView {

    public BaseView() {
        Game.getInstance().registerView(this);
    }

    public abstract void updateView(BaseModel model);

    public void preRender() {}

    public void draw(SpriteBatch batch) {}

    public abstract float renderIndex();

}
