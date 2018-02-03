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
 * This Class is responsible for showing a image containing multiple images as an single image.
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

    public SpriteView(String spritePath) {
        this.spritePath = spritePath;
    }

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

    @Override
    public void render(SpriteBatch batch) {
        if (visible)
            sprite.draw(batch);
    }

    @Override
    public float renderIndex() {
        return sprite.getY();
    }

    @Override
    public void debugRender(ShapeRenderer shapeRenderer) {
        if (!visible)
            return;
        shapeRenderer.setColor(Color.ORANGE);
        shapeRenderer.line(sprite.getX(), sprite.getY(), sprite.getX(), sprite.getY() + sprite.getHeight());
        shapeRenderer.line(sprite.getX() + sprite.getWidth(), sprite.getY(), sprite.getX() + sprite.getWidth(), sprite.getY() + sprite.getHeight());
    }
}
