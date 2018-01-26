package com.parkingtycoon.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.parkingtycoon.helpers.IsometricConverter;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.BluePrintModel;

public class BluePrintView extends AnimatedSpriteView {

    private String animationName;
    private boolean canBuild, fadingIn;
    private Color color = new Color();

    public BluePrintView(String spritePath) {
        super(spritePath, false);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void updateView(BaseModel model) {

        if (model instanceof BluePrintModel) {

            BluePrintModel bluePrint = (BluePrintModel) model;
            animationName = "bluePrintAngle" + bluePrint.getAngle();
            canBuild = bluePrint.canBuild();
            spritePosition.set(bluePrint.x, bluePrint.y);
            IsometricConverter.normalToIsometric(spritePosition);
        }

    }

    @Override
    public void preRender() {

        if (canBuild) {
            float delta = Gdx.graphics.getDeltaTime() * 2;
            if (fadingIn) {
                color.a = Math.min(1, color.a + delta);
                if (color.a == 1) fadingIn = false;
            } else {
                color.a = Math.max(.5f, color.a - delta);
                if (color.a == .5f) fadingIn = true;
            }
        } else color.a = 1;

        color.set(1, canBuild ? 1 : .2f, canBuild ? 1 : .2f, color.a);

        sprite.setPosition((sprite.getX() + spritePosition.x) / 2f, (sprite.getY() + spritePosition.y) / 2f);

        sprite.setColor(color);

        if (currentAnimation == null || !currentAnimation.name().equals(animationName))
            play(animationName, true);
    }

    @Override
    public void debugRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.line(spritePosition.x - .1f, spritePosition.y - .1f, spritePosition.x + .1f, spritePosition.y + .1f);
        shapeRenderer.line(spritePosition.x - .1f, spritePosition.y + .1f, spritePosition.x + .1f, spritePosition.y - .1f);
        super.debugRender(shapeRenderer);
    }
}

