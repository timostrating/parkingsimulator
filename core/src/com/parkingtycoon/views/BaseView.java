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

    /*
    The show method registers the View at the RenderController.
    This method should not be called before the constructor of the View is finished,
    otherwise the RenderController might try to start/preRender/render an uninitialized View.
     */
    public void show() {
        CompositionRoot.getInstance().renderController.registerView(this);
    }

    public void hide() {
        CompositionRoot.getInstance().renderController.unregisterView(this);
    }

    public void start() {}

    public abstract void updateView(BaseModel model);

    public void preRender() {}

    public void draw(SpriteBatch batch) {}

    public void debugRender(ShapeRenderer shapeRenderer) {}

    public abstract float renderIndex();

}
