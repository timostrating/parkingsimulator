package com.parkingtycoon.views;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.models.BaseModel;

/**
 * This class is responsible for adding the responbitities of a View in the MVC architechture.
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
