package com.parkingtycoon.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * This is a helper class for creating drawables.
 */
public class TextureHelper {

    /**
     * Create a Drawable with a texture from URL that is WIDTH, HEIGHT in dimensions
     * @param url the url of the texture.
     * @param minWidth the width of the drawable.
     * @param minHeight the Height of the drawable.
     * @return a Drawable with the settings you entered.
     */
    public static Drawable setupDrawable(String url, float minWidth, float minHeight) {
        Texture buttonTexture = new Texture(Gdx.files.internal(url));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(buttonTexture));

        drawable.setMinWidth(minWidth);
        drawable.setMinHeight(minHeight);

        return drawable;
    }
}
