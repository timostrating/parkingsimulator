package com.parkingtycoon.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.helpers.interfaces.FloorDependable;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.BuildingModel;

import java.util.HashMap;

/**
 * The SpriteView can render a static image to the screen on any position
 */
public class SpriteView extends BaseView {

    public static final HashMap<String, Texture> TEXTURES = new HashMap<String, Texture>() {
        @Override
        public Texture get(Object key) {
            Texture texture = super.get(key);

            if (texture == null && key instanceof String) {
                String path = (String) key;
                texture = new Texture(Gdx.files.internal(path));
                put(path, texture);
            }

            return texture;
        }
    };

    protected boolean visible = true;
    protected Vector2 spritePosition = new Vector2();
    protected Sprite sprite = new Sprite();
    protected String spritePath;

    /**
     * Creates a new SpriteView
     * @param spritePath place where the image can be found
     */
    public SpriteView(String spritePath) {
        this.spritePath = spritePath;
    }

    /**
     * This method is called when the sprite should load its texture
     */
    @Override
    public void start() {
        Texture texture = TEXTURES.get(spritePath);
        sprite.setRegion(texture);
        sprite.setSize(texture.getWidth() / 32f, texture.getHeight() / 32f);
        super.start();
    }

    @Override
    public void updateView(BaseModel model) {

        if (model instanceof FloorDependable)
            visible = ((FloorDependable) model).isOnActiveFloor();

        if (model instanceof BuildingModel) {

            BuildingModel building = (BuildingModel) model;
            sprite.setColor(building.isToBeDemolished() ? Color.RED : Color.WHITE);
            if (building.isDemolished())
                end();
        }
    }

    /**
     * This method wil render the sprite
     * @param batch
     */
    @Override
    public void render(SpriteBatch batch) {
        if (visible)
            sprite.draw(batch);
    }

    /**
     * This method is used to sort the views based on their positions
     * @return Render Index (The higher, the earlier the sprite will be rendered)
     */
    @Override
    public float renderIndex() {
        return sprite.getY();
    }

    /**
     * Shows some useful lines for debugging mode.
     * @param shapeRenderer
     */
    @Override
    public void debugRender(ShapeRenderer shapeRenderer) {
        if (!visible)
            return;
        shapeRenderer.setColor(Color.ORANGE);
        shapeRenderer.line(sprite.getX(), sprite.getY(), sprite.getX(), sprite.getY() + sprite.getHeight());
        shapeRenderer.line(sprite.getX() + sprite.getWidth(), sprite.getY(), sprite.getX() + sprite.getWidth(), sprite.getY() + sprite.getHeight());
    }
}
