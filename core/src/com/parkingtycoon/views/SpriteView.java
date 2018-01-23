package com.parkingtycoon.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.parkingtycoon.Game;
import com.parkingtycoon.models.BaseModel;

import java.util.HashMap;

public class SpriteView extends BaseView {

    private static HashMap<String, Texture> textures = new HashMap<>();

    protected Sprite sprite;
    protected boolean visible = true;
    protected String spritePath;

    public SpriteView(String spritePath) {
        super();
        this.spritePath = spritePath;
    }

    @Override
    public void start() {
        super.start();
        Texture texture = textures.get(spritePath);
        if (texture == null) {
            texture = new Texture(Gdx.files.internal(spritePath));
            textures.put(spritePath, texture);
        }

        sprite = new Sprite(texture);
        sprite.setSize(sprite.getWidth() / 32f, sprite.getHeight() / 32f);
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
        return (sprite.getY() + Game.WORLD_HEIGHT) / (Game.WORLD_HEIGHT * 2f);
    }

}
