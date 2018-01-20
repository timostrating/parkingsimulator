package com.parkingtycoon.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.models.BaseModel;

import java.util.HashMap;

public class SpriteView extends BaseView {

    private static HashMap<String, Texture> textures = new HashMap<>();

    protected Sprite sprite;
    protected Vector2 isometricPosition = new Vector2();
    protected Vector2 offset;
    protected boolean visible = true;

    public SpriteView(String spritePath, Vector2 offset) {
        super();

        Texture texture = textures.get(spritePath);
        if (texture == null) {
            texture = new Texture(Gdx.files.internal(spritePath));
            textures.put(spritePath, texture);
        }

        sprite = new Sprite(texture);
        this.offset = offset == null ? new Vector2() : offset;
        sprite.setSize(sprite.getWidth() / 16f, sprite.getHeight() / 16f);
    }

    @Override
    public void updateView(BaseModel model) {}

    @Override
    public void draw(SpriteBatch batch) {
        if (!visible) return;

        sprite.setPosition(isometricPosition.x + offset.x, isometricPosition.y + offset.y);
        sprite.draw(batch);
    }

}
