package com.parkingtycoon.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.parkingtycoon.models.BaseModel;

import java.util.HashMap;

public class SpriteView extends BaseView {

    private static HashMap<String, Texture> textures = new HashMap<>();

    protected Sprite sprite;
    protected boolean visible = true;

    public SpriteView(String spritePath) {
        super();

        Texture texture = textures.get(spritePath);
        if (texture == null) {
            texture = new Texture(Gdx.files.internal(spritePath));
            textures.put(spritePath, texture);
        }

        sprite = new Sprite(texture);
        sprite.setSize(sprite.getWidth() / 16f, sprite.getHeight() / 16f);
    }

    @Override
    public void updateView(BaseModel model) {}

    @Override
    public void draw(SpriteBatch batch) {
        if (!visible) return;

        sprite.draw(batch);
    }

    @Override
    public float renderIndex() {
        // todo worldHeight = floor height
        int worldHeight = 1080; // Game.getInstance().worldHeight;
        return (sprite.getY() + worldHeight) / (worldHeight * 2f);
    }
}