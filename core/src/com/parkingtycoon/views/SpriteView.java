package com.parkingtycoon.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.models.BaseModel;

public class SpriteView extends BaseView {

    protected Sprite sprite;
    protected Vector2 isometricPosition = new Vector2();
    protected Vector2 offset;
    protected boolean visible = true;

    public SpriteView(String spritePath, Vector2 offset) {
        super();
        sprite = new Sprite(new Texture(Gdx.files.internal(spritePath)));
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
