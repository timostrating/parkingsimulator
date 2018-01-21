package com.parkingtycoon.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.CompositionRoot;
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
        int worldHeight = CompositionRoot.getInstance().floorsController.getHeight();
        return (sprite.getY() + worldHeight) / (worldHeight * 2f);
    }

    public Vector2 normalToIsometric(Vector2 convert) { // HACK

        float normalX = convert.x;
        float normalY = convert.y;

        convert.set(CompositionRoot.getInstance().floorsController.getWidth() * 2, CompositionRoot.getInstance().floorsController.getHeight()); // HACK

        convert.x += normalX * 2;
        convert.y -= normalX;

        convert.x -= normalY * 2;
        convert.y -= normalY;

        return convert;
    }

    public Vector2 isometricToNormal(Vector2 convert) { // HACK

        float isoX = convert.x;
        float isoY = convert.y;

        float xDiff = isoX - CompositionRoot.getInstance().floorsController.getWidth() * 2;  // HACK
        float yDiff = -2 * (isoY - CompositionRoot.getInstance().floorsController.getHeight());  // HACK

        convert.x = (xDiff + yDiff) / 4;
        convert.y = (yDiff - xDiff) / 4;
        return convert;
    }
}
