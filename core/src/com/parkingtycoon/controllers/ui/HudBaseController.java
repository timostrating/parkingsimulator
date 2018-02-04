package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.kotcrab.vis.ui.widget.VisTable;
import com.parkingtycoon.controllers.UpdateableController;

/**
 * This class is responsible for adding the responsibilities of a Controller in the MVC architecture.
 */
public abstract class HudBaseController extends UpdateableController {

    protected final VisTable table;


    public HudBaseController(Stage stage) {
        table = new VisTable();
    }

    public VisTable getTable() {
        return table;
    }

    protected Drawable setupDrawable(String url) {
        return setupDrawable(url, 64, 64);
    }

    protected Drawable setupDrawable(String url, float minWidth, float minHeight) {
        Texture buttonTexture = new Texture(Gdx.files.internal(url));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(buttonTexture));

        drawable.setMinWidth(minWidth);
        drawable.setMinHeight(minHeight);

        return drawable;
    }

    @Override
    public void update() {

    }
}