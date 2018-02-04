package com.parkingtycoon.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TextureHelper {

    public static Drawable setupDrawable(String url, float minWidth, float minHeight) {
        Texture buttonTexture = new Texture(Gdx.files.internal(url));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(buttonTexture));

        drawable.setMinWidth(minWidth);
        drawable.setMinHeight(minHeight);

        return drawable;
    }
}
