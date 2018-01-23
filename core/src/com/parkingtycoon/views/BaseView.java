package com.parkingtycoon.views;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.models.BaseModel;

/**
 * Created by Hilko on 18-1-2018.
 */
public abstract class BaseView {

    BaseView() {
        CompositionRoot.getInstance().renderController.registerView(this);
    }

    public void start() {}

    public abstract void updateView(BaseModel model);

    public void preRender() {}

    public void draw(SpriteBatch batch) {}

    public abstract float renderIndex();

}
