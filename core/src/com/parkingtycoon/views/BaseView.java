package com.parkingtycoon.views;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.interfaces.Renderable;
import com.parkingtycoon.models.BaseModel;

/**
 * This class is responsible for adding the responsibilities of a View in the MVC architecture.
 */
public abstract class BaseView implements Renderable {

    protected BaseView() {
        CompositionRoot.getInstance().renderController.registerView(this);
    }

    public void start() {}

    public abstract void updateView(BaseModel model);

    public void preRender() {}

    public void render(SpriteBatch batch) {}

    public void debugRender(ShapeRenderer shapeRenderer) {}

    public abstract float renderIndex();

}
