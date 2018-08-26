package com.parkingtycoon.views;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.interfaces.Renderable;
import com.parkingtycoon.models.BaseModel;

/**
 * This class is responsible for adding the responsibilities of a View in the MVC architecture.
 *
 * @author GGG
 */
public abstract class BaseView implements Renderable {

    public boolean ended;

    /**
     * The show method registers the View at the RenderController.
     * This method should not be called before the constructor of the View is finished,
     * otherwise the RenderController might try to start/preRender/render an uninitialized View.
     */
    public void show() {
        CompositionRoot.getInstance().renderController.registerView(this);
    }

    /**
     * End your registration.
     */
    public void end() {
        CompositionRoot.getInstance().renderController.unregisterView(this);
        ended = true;
    }

    /**
     *
     */
    public void start() {}

    /**
     * This is the method tha twill be called by a Model in case the view is registered by this model.
     *
     * @param model the Model gives his this to us. The BaseModel can be cast to the specific model type you are looking for.
     */
    public abstract void updateView(BaseModel model);

    /**
     * We just Cleared the screen, But we have not started a new screen jet.
     */
    public void preRender() {}

    /**
     * We just Cleared the screen, and the preRenders have done their thinks.
     *
     * @param batch the batch where you can render your data on.
     */
    public void render(SpriteBatch batch) {}

    /**
     * If you which to render a shape than you can this.
     *
     * @param shapeRenderer the renderer where you can draw lines and shapes.
     */
    public void renderShapes(ShapeRenderer shapeRenderer) {}

    /**
     * If you which to debug some things then you can use this render to draw the data.
     *
     * @param shapeRenderer the renderer where you can draw your debug data on.
     */
    public void debugRender(ShapeRenderer shapeRenderer) {}

    /**
     * We sort the views based on this renderIndex.
     *
     * @return the render index as a float
     */
    public abstract float renderIndex();

}
